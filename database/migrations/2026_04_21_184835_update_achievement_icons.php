<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        $icons = [
            'First step'           => 'first_step.png',
            'Getting Warmed Up'    => 'getting_warmed_up.png',
            'Locked In'            => 'locked_in.png',
            'Habit Formed'         => 'habit_formed.png',
            'On a Roll'            => 'on_a_roll.png',
            'Century Club'         => 'century_club.png',
            'First Bite'           => 'first_bite.png',
            'Hydration Hero'       => 'hydration_hero.png',
            'Clean Plate'          => 'clean_plate.png',
            'First Sweat'          => 'first_sweat.png',
            'Weekly Warrior'       => 'weekly_warrior.png',
            'Iron Will'            => 'iron_will.png',
            'First Breath'         => 'first_breath.png',
            'Finding Peace'        => 'finding_peace.png',
            'Zen Master'           => 'zen_master.png',
            'First Focus'          => 'first_focus.png',
            'Study Streak'         => 'study_streak.png',
            'Consistency Builder'  => 'consistency_builder.png',
            'First Task'           => 'first_task.png',
            'Getting Productive'   => 'getting_productive.png',
            'Work Warrior'         => 'work_warrior.png',
        ];

        foreach ($icons as $name => $icon) {
            DB::table('achievements')
                ->where('name', $name)
                ->update(['icon' => $icon]);
        }
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        //
    }
};
