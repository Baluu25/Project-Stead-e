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

        return response()->json($completion, 201);
    }
}