<?php

namespace Tests\Unit;

use App\Models\Habit;
use App\Models\HabitCompletion;
use App\Models\Goal;
use App\Models\User;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class HabitModelTest extends TestCase
{
    use RefreshDatabase;

    /** @test */
    public function habit_belongs_to_user()
    {
        $user  = User::factory()->create();
        $habit = Habit::factory()->create(['user_id' => $user->id]);

        $this->assertInstanceOf(User::class, $habit->user);
        $this->assertEquals($user->id, $habit->user->id);
    }

    /** @test */
    public function habit_has_many_completions()
    {
        $user  = User::factory()->create();
        $habit = Habit::factory()->create(['user_id' => $user->id]);

        HabitCompletion::factory()->count(3)->create([
            'habit_id' => $habit->id,
            'user_id'  => $user->id,
        ]);

        $this->assertCount(3, $habit->completions);
    }

    /** @test */
    public function habit_belongs_to_goal()
    {
        $user  = User::factory()->create();
        $goal  = Goal::factory()->create(['user_id' => $user->id]);
        $habit = Habit::factory()->create([
            'user_id' => $user->id,
            'goal_id' => $goal->id,
        ]);

        $this->assertInstanceOf(Goal::class, $habit->goal);
        $this->assertEquals($goal->id, $habit->goal->id);
    }

    /** @test */
    public function habit_goal_nullable()
    {
        $user  = User::factory()->create();
        $habit = Habit::factory()->create(['user_id' => $user->id, 'goal_id' => null]);

        $this->assertNull($habit->goal);
    }

    // SoftDelete

    /** @test */
    public function habit_soft_deletable()
    {
        $user  = User::factory()->create();
        $habit = Habit::factory()->create(['user_id' => $user->id]);

        $habit->delete();

        $this->assertSoftDeleted('habits', ['id' => $habit->id]);
        $this->assertNull(Habit::find($habit->id));
    }

    // Casting

    /** @test */
    public function scheduled_days_gives_back_array()
    {
        $user  = User::factory()->create();
        $habit = Habit::factory()->create([
            'user_id'        => $user->id,
            'frequency'      => 'custom',
            'scheduled_days' => [1, 3, 5],
        ]);

        $fresh = Habit::find($habit->id);

        $this->assertIsArray($fresh->scheduled_days);
        $this->assertEquals([1, 3, 5], $fresh->scheduled_days);
    }
}
