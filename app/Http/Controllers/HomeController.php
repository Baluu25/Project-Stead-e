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
        $todaysHabits = auth()->user()->habits()
            ->where('is_active', true)
            ->with(['completions' => function($q) {
            $q->whereDate('completed_at', today());
            }])
            ->get();

       return [
           'current_streak' => $user->current_streak ?? 0,
           'longest_streak' => $user->longest_streak ?? 0,
            'todays_habits' => $todaysHabits,
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
        $todaysHabits = $data['todays_habits'];
        $totalHabits = $todaysHabits->count();
        $completedHabits = $todaysHabits->filter(fn($h) => $h->completions->count() >= ($h->target_count ?? 1))->count();
        $dailyProgressPercent = $totalHabits > 0 ? round(($completedHabits / $totalHabits) * 100) : 0;
        return view('home', [
            'currentStreak' => $data['current_streak'],
            'longestStreak' => $data['longest_streak'],
            'todaysHabits' => $data['todays_habits'],
            'dailyProgressPercent' => $dailyProgressPercent,
            'completedTodayIds' => $data['completed_today_ids'],
        ]);
    }

    public function apiIndex()
    {
        return response()->json($this->getDashboardData());
    }

}