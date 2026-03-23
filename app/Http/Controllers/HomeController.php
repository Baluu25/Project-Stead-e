<?php

namespace App\Http\Controllers;

use App\Models\Habit;
use App\Models\HabitCompletion;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class HomeController extends Controller
{
    private function getDashboardData(): array
    {
        $user   = Auth::user();
        $userId = $user->id;

       return [
           'current_streak' => $user->current_streak ?? 0,
           'longest_streak' => $user->longest_streak ?? 0,
            'todays_habits' => Habit::where('user_id', $userId)->where('is_active', true)->get(),
            'completed_today_ids' => HabitCompletion::where('user_id', $userId)
                ->where('is_skipped', false)
                ->whereDate('completed_at', today())
                ->pluck('habit_id')
                ->toArray(),
        ];
    }

    public function index()
    {
        $data = $this->getDashboardData();
        return view('home', [
            'currentStreak' => $data['current_streak'],
            'longestStreak' => $data['longest_streak'],
            'todaysHabits' => $data['todays_habits'],
            'completedTodayIds' => $data['completed_today_ids'],
        ]);
    }

    public function apiIndex()
    {
        return response()->json($this->getDashboardData());
    }

}