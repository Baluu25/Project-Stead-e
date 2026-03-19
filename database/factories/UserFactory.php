<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\User>
 */
class UserFactory extends Factory
{
    /**
     * The current password being used by the factory.
     */
    protected static ?string $password;

    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'username'   => fake()->unique()->userName(),
            'gender'     => fake()->randomElement(['male', 'female', 'other']),
            'birthdate'  => fake()->dateTimeBetween('-50 years', '-18 years')->format('Y-m-d'),
            'weight'     => fake()->randomFloat(1, 45, 130),
            'height'     => fake()->numberBetween(150, 200),
            'sleep_time' => fake()->randomElement(['21:00','22:00','23:00','00:00']),
            'wake_time'  => fake()->randomElement(['05:00','06:00','07:00','08:00']),
            'user_goal'  => fake()->randomElement(['weight_loss','consistency','quit_habit','explore']),
        ];
    }

    /**
     * Indicate that the model's email address should be unverified.
     */
    public function unverified(): static
    {
        return $this->state(fn (array $attributes) => [
            'email_verified_at' => null,
        ]);
    }
}
