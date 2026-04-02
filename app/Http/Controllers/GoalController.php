<?php

namespace App\Http\Controllers;

use App\Models\Goal;
use App\Http\Requests\StoreGoalRequest;
use App\Http\Requests\UpdateGoalRequest;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Request;

class GoalController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    // index() — list all goals for current user
    public function index() {
        $goals = Auth::user()->goals()->orderBy('status')->orderBy('deadline')->get();
        return view('goals', compact('goals'));
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
    // store() — create a new goal
    public function store(Request $request) {
        $data = $request->validate([
            'title' => 'required|string|max:255',
            'description' => 'nullable|string',
            'icon' => 'nullable|string|max:10',
            'category' => 'required|string',
            'target_value' => 'required|integer|min:1',
            'unit' => 'required|string',
            'deadline' => 'nullable|date|after:today',
        ]);
        $data['user_id'] = Auth::id();
        Goal::create($data);
        return redirect()->route('goals')->with('success', 'Goal created!');
    }

    /**
     * Display the specified resource.
     */
    public function show(Goal $goal)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Goal $goal)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    // update() — edit goal details
    public function update(Request $request, Goal $goal) {
        $goal->update($request->validated());
        return redirect()->route('goals')->with('success', 'Goal updated!');
    }

    public function progress(Request $request, Goal $goal) {
        $goal->increment('current_value', $request->input('amount', 1));
        if ($goal->current_value >= $goal->target_value) {
            $goal->update(['status' => 'completed']);
        }
        return back()->with('success', 'Progress logged!');
    }

    /**
     * Remove the specified resource from storage.
     */
    // destroy() — delete or abandon goal
    public function destroy(Goal $goal) {
        $goal->delete();
        return redirect()->route('goals');
    }
}
