<?php

namespace App\Http\Controllers;

use App\Models\Habit;
use App\Models\HabitCompletion;
use App\Http\Requests\StoreHabitCompletionRequest;
use App\Http\Requests\UpdateHabitCompletionRequest;
use Illuminate\Support\Facades\Auth;

class HabitCompletionController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(StoreHabitCompletionRequest $request)
    {
        $habit = Habit::where('id', $request->habit_id)
                ->where('user_id', Auth::id())
                ->firstOrFail();

        HabitCompletion::create([
            'habit_id'     => $habit->id,
            'user_id'      => Auth::id(),
            'completed_at' => now(),
            'mood'         => $request->mood,
            'notes'        => $request->notes,
            'is_skipped'   => $request->boolean('is_skipped', false),
        ]);
        return redirect()->route('home')->with('success', 'Habit logged!');
    }


    /**
     * Display the specified resource.
     */
    public function show(HabitCompletion $habitCompletion)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(HabitCompletion $habitCompletion)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(UpdateHabitCompletionRequest $request, HabitCompletion $habitCompletion)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(HabitCompletion $habitCompletion)
    {
        //
    }
}
