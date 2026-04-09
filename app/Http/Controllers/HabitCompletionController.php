<?php

namespace App\Http\Controllers;

use App\Models\Habit;
use App\Models\HabitCompletion;
use App\Http\Requests\StoreHabitCompletionRequest;
use App\Http\Requests\UpdateHabitCompletionRequest;
use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;

class HabitCompletionController extends Controller
{
    public function store(StoreHabitCompletionRequest $request)
    {
        $habit = Habit::where('id', $request->habit_id)
        ->where('user_id', Auth::id())
        ->firstOrFail();

        $quantity = max(1, (int) $request->input('quantity', 1));

        HabitCompletion::create([
            'habit_id'     => $habit->id,
            'user_id'      => Auth::id(),
            'completed_at' => now(),
            'quantity'     => $quantity,
            'mood'         => $request->mood,
            'notes'        => $request->notes,
            'is_skipped'   => $request->boolean('is_skipped', false),
        ]);

        $completed = (int) HabitCompletion::where('habit_id', $habit->id)
        ->where('user_id', Auth::id())
        ->whereDate('completed_at', today())
        ->sum('quantity');

        return response()->json(array_merge([
            'completed' => $completed,
            'target'    => $habit->target_count ?? 1,
        ], $this->getDailyStats()), 201);
    }

    public function destroyLast(Request $request, $habitId)
    {
        $habit = Habit::where('id', $habitId)
        ->where('user_id', Auth::id())
        ->firstOrFail();

        $toRemove = max(1, (int) $request->input('amount', 1));

        $completion = HabitCompletion::where('habit_id', $habit->id)
            ->where('user_id', Auth::id())
            ->whereDate('completed_at', today())
            ->latest()
            ->first();

        while ($toRemove > 0 && $completion) {
            if ($completion->quantity <= $toRemove) {
                $toRemove -= $completion->quantity;
                $completion->delete();
                $completion = HabitCompletion::where('habit_id', $habit->id)
                    ->where('user_id', Auth::id())
                    ->whereDate('completed_at', today())
                    ->latest()
                    ->first();
            } else {
                $completion->decrement('quantity', $toRemove);
                $toRemove = 0;
            }
        }

        $completed = (int) HabitCompletion::where('habit_id', $habit->id)
        ->where('user_id', Auth::id())
        ->whereDate('completed_at', today())
        ->sum('quantity');

        return response()->json(array_merge([
            'completed' => $completed,
            'target'    => $habit->target_count ?? 1,
        ], $this->getDailyStats()));
    }

    private function getDailyStats(): array
    {
        $userId = Auth::id();

        $todaysHabits = Habit::where('user_id', $userId)
        ->where('is_active', true)
        ->with(['completions' => function ($q) {
            $q->whereDate('completed_at', today());
        }])
        ->get();

        $total = $todaysHabits->count();
        $completedCount = $todaysHabits->filter(
            fn($h) => $h->completions->sum('quantity') >= ($h->target_count ?? 1)
        )->count();

        return [
            'daily_progress_percent' => $total > 0 ? round(($completedCount / $total) * 100) : 0,
            'has_completion_today'   => HabitCompletion::where('user_id', $userId)
            ->where('is_skipped', false)
            ->whereDate('completed_at', today())
            ->exists(),
        ];
    }
}