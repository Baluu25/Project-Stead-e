<?php

namespace App\Http\Controllers;

use App\Models\Goal;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class GoalController extends Controller
{
    public function index()
    {
        $goals = Auth::user()->goals()->orderBy('status')->orderBy('deadline')->get();
        return view('goals', compact('goals'));
    }

    public function create()
    {
        //
    }

    public function store(Request $request)
    {
        $data = $request->validate([
            'title'        => 'required|string|max:255',
            'description'  => 'nullable|string',
            'icon'         => 'nullable|string|max:100',
            'category'     => 'required|string',
            'target_value' => 'required|integer|min:1',
            'unit'         => 'required|string',
            'deadline'     => 'nullable|date|after:today',
        ]);

        $data['user_id'] = Auth::id();
        Goal::create($data);

        return redirect()->route('goals')->with('success', 'Goal created!');
    }

    public function show(Goal $goal)
    {
        //
    }

    public function edit(Goal $goal)
    {
        //
    }

    public function update(Request $request, Goal $goal)
    {
        $data = $request->validate([
            'title'        => 'required|string|max:255',
            'description'  => 'nullable|string',
            'icon'         => 'nullable|string|max:100',
            'category'     => 'required|string',
            'target_value' => 'required|integer|min:1',
            'unit'         => 'required|string',
            'deadline'     => 'nullable|date|after:today',
        ]);

        $goal->update($data);

        return redirect()->route('goals')->with('success', 'Goal updated!');
    }

    public function progress(Request $request, Goal $goal)
    {
        $amount   = (float) $request->input('amount', 1);
        $newValue = max(0, min($goal->current_value + $amount, $goal->target_value));

        if ($newValue >= $goal->target_value) {
            $goal->update(['current_value' => $newValue, 'status' => 'completed']);
        } elseif ($newValue > 0) {
            $goal->update(['current_value' => $newValue, 'status' => 'in-progress']);
        } else {
            $goal->update(['current_value' => $newValue, 'status' => 'not-started']);
        }

        return back()->with('success', 'Progress logged!');
    }

    public function destroy(Goal $goal)
    {
        $goal->delete();
        return redirect()->route('goals');
    }
}
