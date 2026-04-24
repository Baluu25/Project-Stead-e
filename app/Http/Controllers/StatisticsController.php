<?php

namespace App\Http\Controllers;

use App\Models\Habit;
use App\Models\HabitCompletion;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class StatisticsController extends Controller
{
    public function apiIndex()
    {
        $userId = Auth::id();
        $totalHabits = Habit::where('user_id', $userId)->count();

        $activeHabits = Habit::where('user_id', $userId)
            ->where('is_active', true)
            ->count();

        $completionsThisWeek = HabitCompletion::where('user_id', $userId)
            ->where('is_skipped', false)
            ->whereBetween('completed_at', [now()->startOfWeek(), now()->endOfWeek()])
            ->count();

        $dailyCompletions = HabitCompletion::where('user_id', $userId)
            ->where('is_skipped', false)
            ->where('completed_at', '>=', now()->subDays(6)->startOfDay())
            ->selectRaw('DATE(completed_at) as date, COUNT(DISTINCT habit_id) as count')
            ->groupBy('date')
            ->orderBy('date')
            ->pluck('count', 'date');

        $categoryBreakdown = HabitCompletion::where('habit_completions.user_id', $userId)
            ->join('habits', 'habits.id', '=', 'habit_completions.habit_id')
            ->where('is_skipped', false)
            ->selectRaw('habits.category, COUNT(*) as count')
            ->groupBy('habits.category')
            ->pluck('count', 'category');

        $completionDates = HabitCompletion::where('user_id', $userId)
            ->where('is_skipped', false)
            ->selectRaw('DATE(completed_at) as day')
            ->groupBy('day')
            ->orderBy('day')
            ->pluck('day')
            ->map(fn($d) => Carbon::parse($d)->startOfDay())
            ->toArray();

        $currentStreak = 0;
        $longestStreak = 0;

        if (!empty($completionDates)) {
            $runLength = 1;
            for ($i = 1; $i < count($completionDates); $i++) {
                $diff = $completionDates[$i - 1]->diffInDays($completionDates[$i]);
                if ((int) $diff === 1) {
                    $runLength++;
                } else {
                    $longestStreak = max($longestStreak, $runLength);
                    $runLength = 1;
                }
            }
            $longestStreak = max($longestStreak, $runLength);

            $dateStrings = array_map(fn($d) => $d->toDateString(), $completionDates);
            $checkDay = Carbon::today();
            while (in_array($checkDay->toDateString(), $dateStrings)) {
                $currentStreak++;
                $checkDay->subDay();
            }
        }
        return response()->json([
            'total_habits'          => $totalHabits,
            'active_habits'         => $activeHabits,
            'current_streak'        => $currentStreak,
            'longest_streak'        => $longestStreak,
            'completions_this_week' => $completionsThisWeek,
            'daily_completions'     => $dailyCompletions,
            'category_breakdown'    => $categoryBreakdown,
        ]);
    }
}
