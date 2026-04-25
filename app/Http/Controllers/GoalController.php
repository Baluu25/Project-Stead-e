<?php
namespace App\Http\Controllers;

use App\Models\Goal;
use App\Http\Requests\StoreGoalRequest;
use App\Http\Requests\UpdateGoalRequest;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class GoalController extends Controller
{

    public function index(Request $request)
    {
        $goals = $request->user()
            ->goals()
            ->with('habits')
            ->orderBy('created_at', 'desc')
            ->get();

        return response()->json($goals);
    }

    public function store(StoreGoalRequest $request)
    {
        $data = $request->validated();
        $data['user_id'] = Auth::id();

        Goal::create($data);

        return redirect()->route('goals')->with('success', 'Goal created!');
    }

    public function update(UpdateGoalRequest $request, Goal $goal)
    {
        if ($goal->user_id !== Auth::id()) {
            abort(403);
        }
        
        $this->authorize('update', $goal);

        $goal->update($request->validated());

        return redirect()->route('goals')->with('success', 'Goal updated!');
    }

    public function progress(Request $request, Goal $goal)
    {
        if ($goal->user_id !== Auth::id()) {
            abort(403);
        }

        $amount   = (float) $request->input('amount', 1);
        $newValue = max(0, min($goal->current_value + $amount, $goal->target_value));

        if ($newValue >= $goal->target_value) {
            $status = 'completed';
        } elseif ($newValue > 0) {
            $status = 'in-progress';
        } else {
            $status = 'not-started';
        }

        $goal->update(['current_value' => $newValue, 'status' => $status]);

        if ($request->expectsJson()) {
            return response()->json([
                'current_value' => $goal->current_value,
                'status'        => $goal->status,
                'progress'      => $goal->progress,
            ]);
        }

        return back()->with('success', 'Progress logged!');
    }

    public function destroy(Goal $goal)
    {
        if ($goal->user_id !== Auth::id()) {
            abort(403);
        }

        $goal->delete();
        return redirect()->route('goals');
    }
}