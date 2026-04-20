<?php

namespace App\Http\Controllers;

use App\Models\Habit;
use App\Http\Requests\StoreHabitRequest;
use App\Http\Requests\UpdateHabitRequest;
use Illuminate\Support\Facades\Auth;

class HabitController extends Controller
{
    public function index()
    {
        $goals = Auth::user()->goals()->get();
        return view('habits', compact('goals'));
    }

    public function apiIndex()
    {
        $habits = Habit::where('user_id', Auth::id())
            ->with(['completions' => function ($q) {
                $q->whereDate('completed_at', today());
            }])
            ->with('goal')
            ->orderBy('created_at', 'desc')
            ->get()
            ->map(function ($habit) {
                $habit->completed_today = $habit->completions->count();
                $habit->goal_name = $habit->goal?->title;
                return $habit;
            });

        return response()->json($habits);
    }

    public function store(StoreHabitRequest $request)
    {
        $scheduledDays = $request->frequency === 'daily' ? null : $request->input('scheduled_days');

        $habit = Habit::create([
            'user_id'      => Auth::id(),
            'name'         => $request->name,
            'description'  => $request->description,
            'category'     => $request->category,
            'frequency'    => $request->frequency,
            'scheduled_days' => $scheduledDays,
            'target_count' => $request->target_count ?? 1,
            'unit'         => $request->unit,
            'icon'         => $request->icon ?? 'star',
            'is_active'    => true,
            'goal_id'      => $request->goal_id,
        ]);

        return response()->json($habit, 201);
    }

    public function update(UpdateHabitRequest $request, $id)
    {
        $habit = Habit::where('id', $id)
            ->where('user_id', Auth::id())
            ->firstOrFail();

        $validated = $request->validated();
        $validated['goal_id'] = $request->goal_id;

        if (isset($validated['frequency']) && $validated['frequency'] === 'daily') {
            $validated['scheduled_days'] = null;
        }

        $habit->update($validated);

        return response()->json($habit);
    }

    public function destroy($id)
    {
        $habit = Habit::where('id', $id)
            ->where('user_id', Auth::id())
            ->firstOrFail();

        $habit->delete();

        return response()->json(null, 204);
    }
}
