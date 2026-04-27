<?php

namespace Tests\Unit;

use App\Models\Goal;
use App\Models\Habit;
use App\Models\HabitCompletion;
use App\Models\User;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class GoalModelTest extends TestCase
{
    use RefreshDatabase;

    // progress atribute

    /** @test */
    public function progress_percentage_is_correct()
    {
        $user = User::factory()->create();

        $goal = Goal::factory()->create([
            'user_id'       => $user->id,
            'current_value' => 25,
            'target_value'  => 100,
        ]);

        $this->assertEquals(25, $goal->progress);
    }

    /** @test */
    public function progress_maximum_can_be_hundred()
    {
        $user = User::factory()->create();

        $goal = Goal::factory()->create([
            'user_id'       => $user->id,
            'current_value' => 200,
            'target_value'  => 100,
        ]);

        $this->assertEquals(100, $goal->progress);
    }

    /** @test */
    public function progress_is_zero_if_target_value_is_zero()
    {
        $user = User::factory()->create();

        $goal = Goal::factory()->create([
            'user_id'       => $user->id,
            'current_value' => 0,
            'target_value'  => 0,
        ]);

        $this->assertEquals(0, $goal->progress);
    }

    // Relations

    /** @test */
    public function goal_connects_to_one_user()
    {
        $user = User::factory()->create();
        $goal = Goal::factory()->create(['user_id' => $user->id]);

        $this->assertInstanceOf(User::class, $goal->user);
        $this->assertEquals($user->id, $goal->user->id);
    }

    /** @test */
    public function goal_can_connect_to_multiple_users()
    {
        $user = User::factory()->create();
        $goal = Goal::factory()->create(['user_id' => $user->id]);

        Habit::factory()->count(3)->create([
            'user_id' => $user->id,
            'goal_id' => $goal->id,
        ]);

        $this->assertCount(3, $goal->habits);
    }
}
