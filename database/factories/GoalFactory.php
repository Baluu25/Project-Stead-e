<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Goal>
 */
class GoalFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'user_id'       => \App\Models\User::factory(),
            'title'         => fake()->sentence(3),
            'description'   => fake()->sentence(8),
            'category'      => fake()->randomElement(['Fitness', 'Nutrition', 'Mindfulness', 'Study', 'Work']),
            'target_value'  => fake()->numberBetween(10, 200),
            'current_value' => 0,
            'unit'          => fake()->randomElement(['days', 'times', 'km', 'books', 'minutes', 'custom']),
            'status'        => fake()->randomElement(['not-started', 'in-progress', 'completed']),
            'deadline'      => fake()->optional()->dateTimeBetween('now', '+1 year')?->format('Y-m-d'),
            'icon'          => 'fa-solid fa-star',
        ];
    }
}
