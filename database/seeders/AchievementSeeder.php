<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class AchievementSeeder extends Seeder
{
    /**
     * The canonical achievement definitions shared by every user.
     */
    private static function definitions(): array
    {
        return [
            // Streaks
            ['Streaks',     'First step',             'Complete your first day',                   1,   'first_step.png'],
            ['Streaks',     'Getting Warmed Up',      'Keep your habit alive for 3 days straight.', 3,  'getting_warmed_up.png'],
            ['Streaks',     'Locked In',              'Reach a 7-day streak',                      7,   'locked_in.png'],

            // Milestones
            ['Milestones',  'Habit Formed',           'Complete a habit 10 times total',           10,  'habit_formed.png'],
            ['Milestones',  'On a Roll',              'Complete 50 habits total',                  50,  'on_a_roll.png'],
            ['Milestones',  'Century Club',           'Complete 100 habits total',                 100, 'century_club.png'],

            // Nutrition
            ['Nutrition',   'First Bite',             'Log your first nutrition habit',            1,   'first_bite.png'],
            ['Nutrition',   'Clean Plate',            'Track all meals for 5 full days',           5,   'clean_plate.png'],
            ['Nutrition',   'Fuel Up',                'Complete 7 nutrition habits',               7,   'fuel_up.png'],

            // Fitness
            ['Fitness',     'First Sweat',            'Complete your first workout habit',         1,   'first_sweat.png'],
            ['Fitness',     'Weekly Warrior',         'Work out 3 times in one week',              3,   'weekly_warrior.png'],
            ['Fitness',     'Iron Will',              'Complete 30 fitness habits',                30,  'iron_will.png'],

            // Mindfulness
            ['Mindfulness', 'Calm Start',             'Complete your first mindfulness habit',     1,   'calm_start.png'],
            ['Mindfulness', 'Mind & Body',            'Complete 3 mindfulness habits',             3,   'mind_and_body.png'],
            ['Mindfulness', 'Well-Being',             'Complete 7 mindfulness habits',             7,   'well_being.png'],

            // Study
            ['Study',       'First Focus',            'Log your first study session',              1,   'first_focus.png'],
            ['Study',       'Study Streak',           'Study 5 days in a row',                     5,   'study_streak.png'],
            ['Study',       'Consistency Builder',    'Study 10 total hours',                      10,  'consistency_builder.png'],

            // Work
            ['Work',        'First Task',             'Complete your first work task',             1,   'first_task.png'],
            ['Work',        'Getting Productive',     'Complete 5 tasks',                          5,   'getting_productive.png'],
            ['Work',        'Work Warrior',           'Complete 10 tasks',                         10,  'work_warrior.png'],
        ];
    }

    /**
     * Seed achievement rows for a single user (idempotent – safe to call multiple times).
     */
    public static function seedForUser(int $userId): void
    {
        foreach (self::definitions() as [$type, $name, $description, $threshold, $icon]) {
            \App\Models\Achievement::updateOrCreate(
                ['user_id' => $userId, 'name' => $name],
                [
                    'achievement_type' => $type,
                    'description'      => $description,
                    'threshold_value'  => $threshold,
                    'icon'             => $icon,
                    'progress'         => 0,
                    'unlocked_at'      => null,
                ]
            );
        }
    }

    /**
     * Seed achievement rows for every existing user.
     */
    public function run(): void
    {
        foreach (\App\Models\User::all() as $user) {
            self::seedForUser($user->id);
        }
    }
}
