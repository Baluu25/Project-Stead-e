<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    private array $map = [
        'fa-solid fa-apple-whole'     => 'restaurant',
        'fa-solid fa-carrot'          => 'local_dining',
        'fa-solid fa-lemon'           => 'local_dining',
        'fa-solid fa-bowl-food'       => 'set_meal',
        'fa-solid fa-mug-saucer'      => 'coffee',
        'fa-solid fa-burger'          => 'fastfood',
        'fa-solid fa-fish'            => 'restaurant',
        'fa-solid fa-egg'             => 'kitchen',
        'fa-solid fa-droplet'         => 'water_drop',
        'fa-solid fa-wine-bottle'     => 'wine_bar',
        'fa-solid fa-utensils'        => 'local_dining',
        'fa-solid fa-plate-wheat'     => 'free_breakfast',
        'fa-solid fa-cookie'          => 'cake',
        'fa-solid fa-cake-candles'    => 'cake',
        'fa-solid fa-mug-hot'         => 'local_cafe',
        'fa-solid fa-dumbbell'        => 'fitness_center',
        'fa-solid fa-person-running'  => 'directions_run',
        'fa-solid fa-person-walking'  => 'directions_walk',
        'fa-solid fa-bicycle'         => 'pedal_bike',
        'fa-solid fa-heart-pulse'     => 'monitor_heart',
        'fa-solid fa-fire'            => 'local_fire_department',
        'fa-solid fa-stopwatch'       => 'timer',
        'fa-solid fa-shoe-prints'     => 'nordic_walking',
        'fa-solid fa-weight-scale'    => 'fitness_center',
        'fa-solid fa-person-swimming' => 'pool',
        'fa-solid fa-person-biking'   => 'electric_bike',
        'fa-solid fa-person-hiking'   => 'hiking',
        'fa-solid fa-futbol'          => 'sports_soccer',
        'fa-solid fa-basketball'      => 'sports_basketball',
        'fa-solid fa-dog'             => 'pets',
        'fa-solid fa-heartbeat'       => 'monitor_heart',
        'fa-solid fa-brain'           => 'psychology',
        'fa-solid fa-heart'           => 'favorite',
        'fa-solid fa-spa'             => 'spa',
        'fa-regular fa-face-smile'    => 'sentiment_satisfied',
        'fa-solid fa-feather'         => 'air',
        'fa-solid fa-leaf'            => 'nature',
        'fa-solid fa-om'              => 'self_improvement',
        'fa-solid fa-cloud'           => 'cloud',
        'fa-solid fa-wind'            => 'air',
        'fa-regular fa-moon'          => 'bedtime',
        'fa-solid fa-cat'             => 'pets',
        'fa-solid fa-dove'            => 'nature',
        'fa-regular fa-sun'           => 'wb_sunny',
        'fa-solid fa-tree'            => 'park',
        'fa-solid fa-book'            => 'menu_book',
        'fa-solid fa-book-open'       => 'book',
        'fa-solid fa-graduation-cap'  => 'school',
        'fa-solid fa-pencil'          => 'edit',
        'fa-solid fa-pen'             => 'edit',
        'fa-solid fa-lightbulb'       => 'lightbulb',
        'fa-solid fa-microscope'      => 'biotech',
        'fa-solid fa-flask'           => 'science',
        'fa-solid fa-language'        => 'translate',
        'fa-solid fa-calculator'      => 'calculate',
        'fa-solid fa-glasses'         => 'visibility',
        'fa-solid fa-ruler'           => 'straighten',
        'fa-solid fa-chalkboard'      => 'history_edu',
        'fa-solid fa-school'          => 'school',
        'fa-solid fa-briefcase'       => 'work',
        'fa-solid fa-laptop'          => 'laptop',
        'fa-solid fa-computer'        => 'computer',
        'fa-solid fa-clock'           => 'schedule',
        'fa-solid fa-calendar-check'  => 'event_available',
        'fa-solid fa-chart-line'      => 'trending_up',
        'fa-solid fa-chart-simple'    => 'bar_chart',
        'fa-solid fa-envelope'        => 'email',
        'fa-solid fa-users'           => 'group',
        'fa-solid fa-building'        => 'business',
        'fa-solid fa-phone'           => 'phone',
        'fa-solid fa-file-lines'      => 'description',
        'fa-solid fa-folder'          => 'folder',
        'fa-solid fa-print'           => 'print',
        'fa-bullseye'                 => 'sports',
        'fa-solid fa-bullseye'        => 'sports',
    ];

    public function up(): void
    {
        foreach ($this->map as $fa => $material) {
            DB::table('goals')->where('icon', $fa)->update(['icon' => $material]);
        }

        Schema::table('goals', function (Blueprint $table) {
            $table->string('icon')->default('sports')->change();
        });
    }

    public function down(): void
    {
        Schema::table('goals', function (Blueprint $table) {
            $table->string('icon')->default('fa-bullseye')->change();
        });

        $reversed = array_flip($this->map);
        foreach ($reversed as $material => $fa) {
            DB::table('goals')->where('icon', $material)->update(['icon' => $fa]);
        }
    }
};
