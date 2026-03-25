<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class AchievementSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $userId = 1;

        $achievements = [
            // Streaks
            ['Streaks', 'streak', 'First step',        'Complete your first day',                    1],
            ['Streaks', 'streak', 'Getting Warmed Up', 'Keep your habit alive for 3 days straight.', 3],
            ['Streaks', 'streak', 'Locked In',         'Reach a 7-day streak',                       7],

            // Milestones
            ['Milestones', 'milestone', 'Habit Formed',   'Complete a habit 10 times total',  10],
            ['Milestones', 'milestone', 'On a Roll',      'Complete 50 habits total',         50],
            ['Milestones', 'milestone', 'Century Club',   'Complete 100 habits total',       100],

            // Nutrition
            ['Nutrition', 'completion', 'First Bite',      'Log your first nutrition habit',         1],
            ['Nutrition', 'completion', 'Hydration Hero',  'Log water intake 7 days in a row',       7],
            ['Nutrition', 'completion', 'Clean Plate',     'Track all meals for 5 full days',         5],

            // Fitness
            ['Fitness', 'completion', 'First Sweat',     'Complete your first workout habit',    1],
            ['Fitness', 'completion', 'Weekly Warrior',  'Work out 3 times in one week',         3],
            ['Fitness', 'completion', 'Iron Will',       'Complete 30 fitness habits',          30],

            // Mindfulness
            ['Mindfulness', 'completion', 'First Breath',    'Complete your first mindfulness session', 1],
            ['Mindfulness', 'completion', 'Finding Peace',   'Complete 3 mindfulness sessions',         3],
            ['Mindfulness', 'completion', 'Zen Master',      'Complete 7 mindfulness sessions',         7],

            // Study
            ['Study', 'completion', 'First Focus',           'Log your first study session', 1],
            ['Study', 'completion', 'Study Streak',          'Study 5 days in a row',        5],
            ['Study', 'completion', 'Consistency Builder',   'Study 10 total hours',        10],

            // Work
            ['Work', 'completion', 'First Task',          'Complete your first work task', 1],
            ['Work', 'completion', 'Getting Productive',  'Complete 5 tasks',              5],
            ['Work', 'completion', 'Work Warrior',        'Complete 10 tasks',            10],
        ];

        foreach ($achievements as [$category, $type, $name, $description, $threshold]) {
            \App\Models\Achievement::create([
                'user_id'          => $userId,
                'category'         => $category,
                'achievement_type' => $type,
                'name'             => $name,
                'description'      => $description,
                'threshold_value'  => $threshold,
                'progress'         => 0,
                'unlocked_at'      => null,
            ]);
        }
    }
}
