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

        return response()->json([
            'completed' => $completed,
            'target'    => $habit->target_count ?? 1
        ], 201);
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

        return response()->json([
        'completed' => $completed,
        'target'    => $habit->target_count ?? 1
        ]);
    }
}