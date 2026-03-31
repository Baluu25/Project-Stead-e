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
        return view('habits');
    }

    public function apiIndex()
    {
        $habits = Habit::where('user_id', Auth::id())
            ->orderBy('created_at', 'desc')
            ->get();

        return response()->json($habits);
    }


    public function store(StoreHabitRequest $request)
    {
        $habit = Habit::create([
            'user_id'      => Auth::id(),
            'name'         => $request->name,
            'description'  => $request->description,
            'category'     => $request->category,
            'frequency'    => $request->frequency,
            'target_count' => $request->target_count ?? 1,
            'icon'         => $request->icon ?? 'star',
            'is_active'    => true,
        ]);

        return response()->json($habit, 201);
    }

    public function update(UpdateHabitRequest $request, $id)
    {
        $habit = Habit::where('id', $id)
            ->where('user_id', Auth::id())
            ->firstOrFail();

        $habit->update($request->validated());

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