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
        $userId = \App\Models\User::first()->id;

        $achievements = [
            // Streaks
            ['Streaks', 'First step', 'Complete your first day', 1],
            ['Streaks', 'Getting Warmed Up', 'Keep your habit alive for 3 days straight.', 3],
            ['Streaks', 'Locked In', 'Reach a 7-day streak', 7],

            // Milestones
            ['Milestones','Habit Formed', 'Complete a habit 10 times total', 10],
            ['Milestones', 'On a Roll', 'Complete 50 habits total', 50],
            ['Milestones', 'Century Club', 'Complete 100 habits total', 100],

            // Nutrition
            ['Nutrition', 'First Bite', 'Log your first nutrition habit', 1],
            ['Nutrition', 'Hydration Hero', 'Log water intake 7 days in a row', 7],
            ['Nutrition', 'Clean Plate', 'Track all meals for 5 full days', 5],

            // Fitness
            ['Fitness', 'First Sweat', 'Complete your first workout habit', 1],
            ['Fitness', 'Weekly Warrior', 'Work out 3 times in one week', 3],
            ['Fitness', 'Iron Will', 'Complete 30 fitness habits', 30],

            // Mindfulness
            ['Mindfulness', 'First Breath', 'Complete your first mindfulness session', 1],
            ['Mindfulness', 'Finding Peace', 'Complete 3 mindfulness sessions', 3],
            ['Mindfulness', 'Zen Master', 'Complete 7 mindfulness sessions', 7],

            // Study
            ['Study', 'First Focus', 'Log your first study session', 1],
            ['Study', 'Study Streak', 'Study 5 days in a row', 5],
            ['Study', 'Consistency Builder', 'Study 10 total hours', 10],

            // Work
            ['Work', 'First Task', 'Complete your first work task', 1],
            ['Work', 'Getting Productive',  'Complete 5 tasks', 5],
            ['Work', 'Work Warrior', 'Complete 10 tasks', 10],
        ];

        foreach ($achievements as [$achievement_type, $name, $description, $threshold]) {
            \App\Models\Achievement::create([
            'user_id'          => $userId,
            'achievement_type' => $achievement_type,
            'name'             => $name,
            'description'      => $description,
            'threshold_value'  => $threshold,
            'progress'         => 0,
            'unlocked_at'      => null,
            ]);
        }
    }
}
