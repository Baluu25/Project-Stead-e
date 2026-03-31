<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\HabitCompletion>
 */
class HabitCompletionFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'habit_id'     => \App\Models\Habit::factory(),
            'user_id'      => \App\Models\User::factory(),
            'completed_at' => fake()->dateTimeBetween('-30 days', 'now'),
            'quantity'     => fake()->numberBetween(1, 10),
            'notes'        => fake()->optional()->sentence(),
            'mood'         => fake()->optional()->randomElement(['happy', 'neutral', 'sad', 'energetic', 'tired']),
            'is_skipped'   => fake()->boolean(10),
        ];
    }
}
