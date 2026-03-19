<?php

namespace App\Http\Controllers;

use App\Models\Habit;
use App\Models\HabitCompletion;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class StatisticsController extends Controller
{
    public function index()
{
    // Logged-in user id
    $user = Auth::user();
    $userId = $user->id;

    // Total active habits count
    $totalHabits = Habit::where('user_id', $userId)->where('is_active', true)->count();

    // This week completed habits
    $completionsThisWeek = HabitCompletion::where('user_id', $userId)
        ->where('is_skipped', false)
        ->whereBetween('completed_at', [now()->startOfWeek(), now()->endOfWeek()])
        ->count();

    // Todays completed habits
    $dailyCompletions = HabitCompletion::where('user_id', $userId)
        ->where('is_skipped', false)
        ->where('completed_at', '>=', now()->subDays(29))
        ->selectRaw('DATE(completed_at) as date, COUNT(*) as count')
        ->groupBy('date')
        ->orderBy('date')
        ->pluck('count', 'date');

    $habits = Habit::where('user_id', $userId)->where('is_active', true)->get();

    // Habit categories
    $categoryBreakdown = HabitCompletion::where('habit_completions.user_id', $userId)
        ->join('habits', 'habits.id', '=', 'habit_completions.habit_id')
        ->where('is_skipped', false)
        ->selectRaw('habits.category, COUNT(*) as count')
        ->groupBy('habits.category')
        ->pluck('count', 'category');

    return view('statistics', compact(
        'totalHabits', 'completionsThisWeek',
        'dailyCompletions', 'categoryBreakdown',
        'user'
    ));
}
}
