<?php

namespace Database\Factories;

use App\Models\Habit;
use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Habit>
 */
class HabitFactory extends Factory
{
    /**
     * Kategorizált ikonok
     */
    protected $categoryIcons = [
        'Nutrition' => [
            'fa-solid fa-apple',
            'fa-solid fa-carrot',
            'fa-solid fa-lemon',
            'fa-solid fa-bowl-food',
            'fa-solid fa-mug-saucer',
            'fa-solid fa-burger',
            'fa-solid fa-fish',
            'fa-solid fa-egg',
            'fa-solid fa-droplet',
            'fa-solid fa-wine-bottle',
        ],
        
        'Fitness' => [
            'fa-solid fa-dumbbell',
            'fa-solid fa-person-running',
            'fa-solid fa-person-walking',
            'fa-solid fa-bicycle',
            'fa-solid fa-heart-pulse',
            'fa-solid fa-fire',
            'fa-solid fa-stopwatch',
            'fa-solid fa-shoe-prints',
            'fa-solid fa-weight-scale',
            'fa-solid fa-person-swimming',
        ],
        
        'Mindfulness' => [
            'fa-solid fa-brain',
            'fa-solid fa-heart',
            'fa-solid fa-spa',
            'fa-regular fa-face-smile',
            'fa-solid fa-feather',
            'fa-solid fa-leaf',
            'fa-solid fa-om',
            'fa-solid fa-cloud',
            'fa-solid fa-wind',
            'fa-regular fa-moon',
        ],
        
        'Study' => [
            'fa-solid fa-book',
            'fa-solid fa-book-open',
            'fa-solid fa-graduation-cap',
            'fa-solid fa-pencil',
            'fa-solid fa-pen',
            'fa-solid fa-brain',
            'fa-solid fa-lightbulb',
            'fa-solid fa-microscope',
            'fa-solid fa-flask',
            'fa-solid fa-language',
        ],
        
        'Work' => [
            'fa-solid fa-briefcase',
            'fa-solid fa-laptop',
            'fa-solid fa-computer',
            'fa-solid fa-clock',
            'fa-solid fa-calendar-check',
            'fa-solid fa-chart-line',
            'fa-solid fa-chart-simple',
            'fa-solid fa-envelope',
            'fa-solid fa-users',
            'fa-solid fa-mug-hot',
        ],
    ];

    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        $categories = array_keys($this->categoryIcons); // ['Nutrition','Fitness','Mindfullness','Study','Work']
        $category = fake()->randomElement($categories);
        
        // Kategória alapján ikon választás
        $icons = $this->categoryIcons[$category];
        $icon = fake()->randomElement($icons);

        return [
            'user_id'      => User::factory(),
            'name'         => fake()->words(3, true),
            'description'  => fake()->sentence(10),
            'category'     => $category,
            'frequency'    => fake()->randomElement(['daily','weekly','monthly','custom']),
            'target_count' => fake()->numberBetween(1, 10),
            'icon'         => $icon,
            'is_active'    => fake()->boolean(85),
        ];
    }
}