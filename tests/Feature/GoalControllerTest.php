<?php

namespace Tests\Feature;

use App\Models\Goal;
use App\Models\Habit;
use App\Models\HabitCompletion;
use App\Models\User;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class GoalControllerTest extends TestCase
{
    use RefreshDatabase;

    private function loginUser(): User
    {
        $user = User::factory()->create();
        $this->actingAs($user);
        return $user;
    }

    // index

    /** @test */
    public function api_index_only_gives_back_own_goals()
    {
        $user  = $this->loginUser();
        $other = User::factory()->create();

        Goal::factory()->create(['user_id' => $user->id,  'title' => 'own goal']);
        Goal::factory()->create(['user_id' => $other->id, 'title' => 'different goal']);

        $response = $this->getJson('/api/goals');

        $response->assertOk()
                 ->assertJsonCount(1)
                 ->assertJsonPath('0.title', 'own goal');
    }

    // store

    /** @test */
    public function store_makes_a_new_goal()
    {
        $user = $this->loginUser();

        $payload = [
            'title'        => 'Run 100 km',
            'description'  => 'Run a total of 100 km',
            'category'     => 'Fitness',
            'target_value' => 100,
            'unit'         => 'km',
            'status'       => 'not-started',
        ];

        $response = $this->postJson('/api/goals', $payload);

        $response->assertCreated()
                 ->assertJsonPath('title', 'Run a total of 100 km');

        $this->assertDatabaseHas('goals', [
            'user_id' => $user->id,
            'title'   => 'Run a total of 100 km',
        ]);
    }

    // update

    /** @test */
    public function update_modifies_own_goal()
    {
        $user = $this->loginUser();
        $goal = Goal::factory()->create(['user_id' => $user->id, 'title' => 'Old Title']);

        $response = $this->putJson("/api/goals/{$goal->id}", [
            'title'        => 'New Title',
            'target_value' => 50,
            'status'       => 'in-progress',
        ]);

        $response->assertOk()
                 ->assertJsonPath('title', 'New Title');

        $this->assertDatabaseHas('goals', ['id' => $goal->id, 'title' => 'New Title']);
    }

    /** @test */
    public function update_does_not_modify_other_user_goal()
    {
        $this->loginUser();
        $other = User::factory()->create();
        $goal  = Goal::factory()->create(['user_id' => $other->id]);

        $response = $this->putJson("/api/goals/{$goal->id}", [
            'title'        => 'Hack',
            'target_value' => 1,
        ]);

        $response->assertForbidden();
    }

    // progress

    /** @test */
    public function progress_increases_current_value()
    {
        $user = $this->loginUser();
        $goal = Goal::factory()->create([
            'user_id'       => $user->id,
            'current_value' => 10,
            'target_value'  => 100,
            'status'        => 'in-progress',
        ]);

        $response = $this->postJson("/api/goals/{$goal->id}/progress", ['amount' => 20]);

        $response->assertOk()
                 ->assertJsonPath('current_value', 30)
                 ->assertJsonPath('status', 'in-progress');
    }

    /** @test */
    public function progress_gets_completed_status_if_reaches_target()
    {
        $user = $this->loginUser();
        $goal = Goal::factory()->create([
            'user_id'       => $user->id,
            'current_value' => 90,
            'target_value'  => 100,
            'status'        => 'in-progress',
        ]);

        $response = $this->postJson("/api/goals/{$goal->id}/progress", ['amount' => 15]);

        $response->assertOk()
                 ->assertJsonPath('current_value', 100)
                 ->assertJsonPath('status', 'completed');
    }

    /** @test */
    public function progress_does_not_get_above_target_value()
    {
        $user = $this->loginUser();
        $goal = Goal::factory()->create([
            'user_id'       => $user->id,
            'current_value' => 95,
            'target_value'  => 100,
        ]);

        $this->postJson("/api/goals/{$goal->id}/progress", ['amount' => 999]);

        $this->assertDatabaseHas('goals', ['id' => $goal->id, 'current_value' => 100]);
    }

    /** @test */
    public function progress_does_not_modify_other_user_goal()
    {
        $this->loginUser();
        $other = User::factory()->create();
        $goal  = Goal::factory()->create(['user_id' => $other->id, 'current_value' => 0]);

        $response = $this->postJson("/api/goals/{$goal->id}/progress", ['amount' => 10]);

        $response->assertForbidden();
    }


    /** @test */
    public function destroy_deletes_own_goal()
    {
        $user = $this->loginUser();
        $goal = Goal::factory()->create(['user_id' => $user->id]);

        $response = $this->deleteJson("/api/goals/{$goal->id}");

        $response->assertNoContent();
        $this->assertDatabaseMissing('goals', ['id' => $goal->id]);
    }

    /** @test */
    public function destroy_does_not_delete_other_user_goal()
    {
        $this->loginUser();
        $other = User::factory()->create();
        $goal  = Goal::factory()->create(['user_id' => $other->id]);

        $response = $this->deleteJson("/api/goals/{$goal->id}");

        $response->assertForbidden();
        $this->assertDatabaseHas('goals', ['id' => $goal->id]);
    }
}
