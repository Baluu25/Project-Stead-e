<?php

namespace Database\Seeders;

use App\Models\User;
use Hash;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        User::create([
            'name' => 'Test User',
            'username' => 'testuser',
            'email' => 'test@example.com',
            'password' => Hash::make('password'),
            'gender' => 'male',
            'birthdate' => '1990-01-01',
            'weight' => 70,
            'height' => 175,
            'sleep_time' => '23:00',
            'wake_time' => '07:00',
            'user_goal' => 'consistency',
        ]);
    }
}
