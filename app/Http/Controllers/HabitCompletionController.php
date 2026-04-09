<?php

namespace App\Http\Controllers;

use App\Models\Habit;
use App\Models\HabitCompletion;
use App\Http\Requests\StoreHabitCompletionRequest;
use App\Http\Requests\UpdateHabitCompletionRequest;
use Illuminate\Support\Facades\Auth;

class HabitCompletionController extends Controller
{
    public function store(StoreHabitCompletionRequest $request)
    {
        $habit = Habit::where('id', $request->habit_id)
            ->where('user_id', Auth::id())
            ->firstOrFail();

        $completion = HabitCompletion::create([
            'habit_id'     => $habit->id,
            'user_id'      => Auth::id(),
            'completed_at' => now(),
            'mood'         => $request->mood,
            'notes'        => $request->notes,
            'is_skipped'   => $request->boolean('is_skipped', false),
        ]);

        $newCount = HabitCompletion::where('habit_id', $habit->id)
        ->where('user_id', Auth::id())
        ->whereDate('completed_at', today())
        ->count();

        return response()->json([
            'completed' => $newCount,
            'target'    => $habit->target_count
        ], 201);
    }

    public function destroyLast($habitId)
    {
        $habit = Habit::where('id', $habitId)
        ->where('user_id', Auth::id())
        ->firstOrFail();

        $completion = HabitCompletion::where('habit_id', $habit->id)
        ->where('user_id', Auth::id())
        ->whereDate('completed_at', today())
        ->latest()
        ->first();

        if (!$completion) {
            return response()->json(['message' => 'No completion to remove'], 404);
        }
        $completion->delete();
        $newCount = HabitCompletion::where('habit_id', $habit->id)
        ->where('user_id', Auth::id())
        ->whereDate('completed_at', today())
        ->count();

        return response()->json(['completed' => $newCount, 'target' => $habit->target_count]);
    }
}