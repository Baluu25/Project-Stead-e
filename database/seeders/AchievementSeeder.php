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
            ['Streaks', 'First step', 'Complete your first day', 1, 'first_step.png'],
            ['Streaks', 'Getting Warmed Up', 'Keep your habit alive for 3 days straight.', 3, 'getting_warmed_up.png'],
            ['Streaks', 'Locked In', 'Reach a 7-day streak', 7, 'locked_in.png'],

            // Milestones
            ['Milestones', 'Habit Formed', 'Complete a habit 10 times total', 10, 'habit_formed.png'],
            ['Milestones', 'On a Roll', 'Complete 50 habits total', 50, 'on_a_roll.png'],
            ['Milestones', 'Century Club', 'Complete 100 habits total', 100, 'century_club.png'],

            // Nutrition
            ['Nutrition', 'First Bite', 'Log your first nutrition habit', 1, 'first_bite.png'],
            ['Nutrition', 'Hydration Hero', 'Log water intake 7 days in a row', 7, 'hydration_hero.png'],
            ['Nutrition', 'Clean Plate',           'Track all meals for 5 full days',          5,   'clean_plate.png'],

            // Fitness
            ['Fitness', 'First Sweat', 'Complete your first workout habit', 1, 'first_sweat.png'],
            ['Fitness', 'Weekly Warrior', 'Work out 3 times in one week', 3, 'weekly_warrior.png'],
            ['Fitness', 'Iron Will', 'Complete 30 fitness habits', 30, 'iron_will.png'],
            // Mindfulness
            ['Mindfulness', 'First Breath', 'Complete your first mindfulness session', 1, 'first_breath.png'],
            ['Mindfulness', 'Finding Peace', 'Complete 3 mindfulness sessions', 3, 'finding_peace.png'],
            ['Mindfulness', 'Zen Master', 'Complete 7 mindfulness sessions', 7, 'zen_master.png'],

            // Study
            ['Study', 'First Focus', 'Log your first study session', 1, 'first_focus.png'],
            ['Study', 'Study Streak', 'Study 5 days in a row', 5, 'study_streak.png'],
            ['Study', 'Consistency Builder', 'Study 10 total hours', 10, 'consistency_builder.png'],

            // Work
            ['Work', 'First Task', 'Complete your first work task', 1, 'first_task.png'],
            ['Work', 'Getting Productive', 'Complete 5 tasks', 5, 'getting_productive.png'],
            ['Work', 'Work Warrior', 'Complete 10 tasks', 10, 'work_warrior.png'],
        ];

        foreach ($achievements as [$achievement_type, $name, $description, $threshold, $icon]) {
            \App\Models\Achievement::create([
            'user_id'          => $userId,
            'achievement_type' => $achievement_type,
            'name'             => $name,
            'description'      => $description,
            'threshold_value'  => $threshold,
            'icon'             => $icon,
            'progress'         => 0,
            'unlocked_at'      => null,
            ]);
        }   
    }
}
