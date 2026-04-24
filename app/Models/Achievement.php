<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Carbon\Carbon;

class Achievement extends Model
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'name',
        'description',
        'threshold_value',
        'icon',
        'achievement_type',
        'progress',
        'unlocked_at',
    ];

    public static function syncForUser(int $userId): array
    {
        $currentStreak       = self::calculateCurrentStreak($userId);
        $totalCompletions    = self::calculateTotalCompletions($userId);
        $categoryCompletions = self::calculateCategoryCompletions($userId);

        $newlyUnlocked = [];
        $achievements  = self::where('user_id', $userId)->get();

        foreach ($achievements as $achievement) {

            if ($achievement->unlocked_at !== null) {
                continue;
            }

            if ($achievement->achievement_type === 'Streaks') {
                $newProgress = $currentStreak;

            } elseif ($achievement->achievement_type === 'Milestones') {
                $newProgress = $totalCompletions;

            } else {
                $type        = strtolower($achievement->achievement_type);
                $newProgress = isset($categoryCompletions[$type]) ? $categoryCompletions[$type] : 0;
            }

            Achievement::where('id', $achievement->id)->update([
                'progress'    => $newProgress,
                'unlocked_at' => $newProgress >= $achievement->threshold_value ? now() : null,
            ]);
            if ($newProgress >= $achievement->threshold_value) {
                $achievement->progress    = $newProgress;
                $achievement->unlocked_at = now();
                $newlyUnlocked[] = $achievement;
            }
        }

        return $newlyUnlocked;
    }

    private static function calculateCurrentStreak(int $userId): int
    {
        $dates = HabitCompletion::where('user_id', $userId)
            ->where('is_skipped', false)
            ->selectRaw('DATE(completed_at) as day')
            ->groupBy('day')
            ->pluck('day')
            ->toArray();

        if (empty($dates)) {
            return 0;
        }

        $streak   = 0;
        $checkDay = Carbon::today();

        while (in_array($checkDay->toDateString(), $dates)) {
            $streak++;
            $checkDay->subDay();
        }

        return $streak;
    }
    private static function calculateTotalCompletions(int $userId): int
    {
        return (int) HabitCompletion::where('user_id', $userId)
            ->where('is_skipped', false)
            ->count();
    }
    private static function calculateCategoryCompletions(int $userId): array
    {
        return HabitCompletion::where('habit_completions.user_id', $userId)
            ->where('is_skipped', false)
            ->join('habits', 'habits.id', '=', 'habit_completions.habit_id')
            ->whereNotNull('habits.category')
            ->selectRaw('LOWER(habits.category) as category, COUNT(*) as count')
            ->groupBy('habits.category')
            ->pluck('count', 'category')
            ->toArray();
    }   
}
