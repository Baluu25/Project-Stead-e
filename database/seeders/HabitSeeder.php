<?php

namespace Database\Seeders;

use App\Models\Habit;
use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class HabitSeeder extends Seeder
{

    public function run(): void
    {
        $user = User::find(1);
        Habit::factory()
            ->count(20)
            ->create([
                'user_id' => 1
            ]);

    }
}
