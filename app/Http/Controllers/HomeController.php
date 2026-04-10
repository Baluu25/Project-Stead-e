<?php

namespace App\Http\Controllers;

use App\Models\Habit;
use App\Models\HabitCompletion;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Carbon\Carbon;

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

        $start = now()->subDays(6)->startOfDay();
        $end   = now()->endOfDay();
        $daysWithCompletion = HabitCompletion::where('user_id', $userId)
        ->where('is_skipped', false)
        ->whereBetween('completed_at', [$start, $end])
        ->selectRaw('DATE(completed_at) as day')
        ->groupBy('day')
        ->pluck('day')
        ->flip()
        ->all();

        $streakDays = [];
        for ($i = 6; $i >= 0; $i--) {
            $date = now()->subDays($i)->startOfDay();
            $streakDays[] = [
                'label'     => $date->format('D')[0] . strtolower(substr($date->format('D'), 1, 1)),
                'completed' => isset($daysWithCompletion[$date->toDateString()]),
            ];
        }

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
            if ($diff === 1) {
                $runLength++;
            } else {
            $longestStreak = max($longestStreak, $runLength);
            $runLength = 1;
            }
        }
        $longestStreak = max($longestStreak, $runLength);

        $checkDay = Carbon::today()->startOfDay();
        $dateSet  = array_flip(array_map(fn($d) => $d->toDateString(), $completionDates));
        while (isset($dateSet[$checkDay->toDateString()])) {
            $currentStreak++;
            $checkDay->subDay();
        }
    }

        return [
            'todays_habits' => $todaysHabits,
            'completed_today_ids' => HabitCompletion::where('user_id', $userId)
                ->where('is_skipped', false)
                ->whereDate('completed_at', today())
                ->pluck('habit_id')
                ->toArray(),
            'streak_days' => $streakDays,
            'current_streak' => $currentStreak,
            'longest_streak' => $longestStreak,
        ];
    }

    public function index()
    {
        $data = $this->getDashboardData();
        $todaysHabits = $data['todays_habits'];
        $totalHabits = $todaysHabits->count();
        $completedHabits = $todaysHabits->filter(fn($h) => $h->completions->sum('quantity') >= ($h->target_count ?? 1))->count();
        $dailyProgressPercent = $totalHabits > 0 ? round(($completedHabits / $totalHabits) * 100) : 0;
        return view('home', [
            'currentStreak' => $data['current_streak'],
            'longestStreak' => $data['longest_streak'],
            'todaysHabits' => $data['todays_habits'],
            'dailyProgressPercent' => $dailyProgressPercent,
            'completedTodayIds' => $data['completed_today_ids'],
            'streakDays' => $data['streak_days']
        ]);
    }

    public function apiIndex()
    {
        return response()->json($this->getDashboardData());
    }

}