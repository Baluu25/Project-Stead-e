<?php

namespace App\Http\Controllers;

use App\Models\Habit;
use App\Models\HabitCompletion;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class HomeController extends Controller
{
    public function index()
    {
        $user   = Auth::user();
        $userId = $user->id;

        $todaysHabits = Habit::where('user_id', $userId)
            ->where('is_active', true)
            ->get();

        $completedTodayIds = HabitCompletion::where('user_id', $userId)
            ->where('is_skipped', false)
            ->whereDate('completed_at', today())
            ->pluck('habit_id')
            ->toArray();

        return response()->json([
            'current_streak'      => $user->current_streak ?? 0,
            'longest_streak'      => $user->longest_streak ?? 0,
            'todays_habits'       => $todaysHabits,
            'completed_today_ids' => $completedTodayIds,
        ]);
    }
}