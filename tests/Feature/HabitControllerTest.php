<?php

namespace Tests\Feature;

use App\Models\Goal;
use App\Models\Habit;
use App\Models\HabitCompletion;
use App\Models\User;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class HabitControllerTest extends TestCase
{
    use RefreshDatabase;

    // login

    private function loginUser(): User
    {
        $user = User::factory()->create();
        $this->actingAs($user);
        return $user;
    }

    // apiIndex

    /** @test */
    public function apiIndex_only_gives_back_own_habits()
    {
        $user  = $this->loginUser();
        $other = User::factory()->create();

        Habit::factory()->create(['user_id' => $user->id,  'name' => 'Own Habit']);
        Habit::factory()->create(['user_id' => $other->id, 'name' => 'Other User Habit']);

        $response = $this->getJson('/api/habits');

        $response->assertOk()
                 ->assertJsonCount(1)
                 ->assertJsonPath('0.name', 'Own Habit');
    }

    /** @test */
    public function api_index_marks_todays_completed_habits()
    {
        $user  = $this->loginUser();
        $habit = Habit::factory()->create(['user_id' => $user->id]);

        // Today completed
        HabitCompletion::factory()->create([
            'habit_id'     => $habit->id,
            'user_id'      => $user->id,
            'completed_at' => now(),
        ]);

        $response = $this->getJson('/api/habits');

        $response->assertOk()
                 ->assertJsonPath('0.completed_today', 1);
    }

    /** @test */
    public function api_index_does_not_mark_old_progress()
    {
        $user  = $this->loginUser();
        $habit = Habit::factory()->create(['user_id' => $user->id]);

        // Completed yesterday
        HabitCompletion::factory()->create([
            'habit_id'     => $habit->id,
            'user_id'      => $user->id,
            'completed_at' => now()->subDay(),
        ]);

        $response = $this->getJson('/api/habits');

        $response->assertOk()
                 ->assertJsonPath('0.completed_today', 0);
    }

    // store

    /** @test */
    public function store_make_new_habit()
    {
        $user = $this->loginUser();

        $payload = [
            'name'        => 'Running',
            'description' => 'Morning run',
            'category'    => 'Fitness',
            'frequency'   => 'daily',
            'target_count'=> 1,
            'icon'        => 'fa-solid fa-person-running',
        ];

        $response = $this->postJson('/api/habits', $payload);

        $response->assertCreated()
                 ->assertJsonPath('name', 'Running')
                 ->assertJsonPath('category', 'Fitness');

        $this->assertDatabaseHas('habits', [
            'user_id' => $user->id,
            'name'    => 'Running',
        ]);
    }

    /** @test */
    public function store_with_daily_frequency_scheduled_days_should_be_null()
    {
        $this->loginUser();

        $payload = [
            'name'           => 'Meditating',
            'category'       => 'Mindfulness',
            'frequency'      => 'daily',
            'scheduled_days' => [1, 3, 5],
        ];

        $response = $this->postJson('/api/habits', $payload);

        $response->assertCreated();
        $this->assertNull($response->json('scheduled_days'));
    }

    /** @test */
    public function store_validation_error_when_missing_name()
    {
        $this->loginUser();

        $response = $this->postJson('/api/habits', [
            'category'  => 'Fitness',
            'frequency' => 'daily',
        ]);

        $response->assertUnprocessable()
                 ->assertJsonValidationErrors(['name']);
    }

    /** @test */
    public function store_validation_error_when_category_invalid()
    {
        $this->loginUser();

        $response = $this->postJson('/api/habits', [
            'name'      => 'Test',
            'category'  => 'InvalidCategory',
            'frequency' => 'daily',
        ]);

        $response->assertUnprocessable()
                 ->assertJsonValidationErrors(['category']);
    }

    /** @test */
    public function store_goal_id_assignible_to_habit()
    {
        $user = $this->loginUser();
        $goal = Goal::factory()->create(['user_id' => $user->id]);

        $payload = [
            'name'      => 'Workout',
            'category'  => 'Fitness',
            'frequency' => 'daily',
            'goal_id'   => $goal->id,
        ];

        $response = $this->postJson('/api/habits', $payload);

        $response->assertCreated()
                 ->assertJsonPath('goal_id', $goal->id);
    }

    // update

    /** @test */
    public function update_modifies_own_habit()
    {
        $user  = $this->loginUser();
        $habit = Habit::factory()->create(['user_id' => $user->id, 'name' => 'Old']);

        $response = $this->putJson("/api/habits/{$habit->id}", [
            'name'      => 'New Name',
            'category'  => 'Fitness',
            'frequency' => 'weekly',
        ]);

        $response->assertOk()
                 ->assertJsonPath('name', 'New Name');

        $this->assertDatabaseHas('habits', ['id' => $habit->id, 'name' => 'New Name']);
    }

    /** @test */
    public function update_cant_modify_other_users_habits()
    {
        $this->loginUser();
        $other = User::factory()->create();
        $habit = Habit::factory()->create(['user_id' => $other->id]);

        $response = $this->putJson("/api/habits/{$habit->id}", [
            'name'      => 'Hack',
            'category'  => 'Fitness',
            'frequency' => 'daily',
        ]);

        $response->assertNotFound();
    }

    /** @test */
    public function update_deletes_scheduled_days_if_daily_frequency()
    {
        $user  = $this->loginUser();
        $habit = Habit::factory()->create([
            'user_id'        => $user->id,
            'frequency'      => 'weekly',
            'scheduled_days' => [1, 3],
        ]);

        $response = $this->putJson("/api/habits/{$habit->id}", [
            'name'      => $habit->name,
            'category'  => $habit->category,
            'frequency' => 'daily',
        ]);

        $response->assertOk();
        $this->assertNull($response->json('scheduled_days'));
    }

    // destroy

    /** @test */
    public function destroy_deletes_own_habit()
    {
        $user  = $this->loginUser();
        $habit = Habit::factory()->create(['user_id' => $user->id]);

        $response = $this->deleteJson("/api/habits/{$habit->id}");

        $response->assertNoContent();
        $this->assertSoftDeleted('habits', ['id' => $habit->id]);
    }

    /** @test */
    public function destroy_does_not_delete_other_users_habits()
    {
        $this->loginUser();
        $other = User::factory()->create();
        $habit = Habit::factory()->create(['user_id' => $other->id]);

        $response = $this->deleteJson("/api/habits/{$habit->id}");

        $response->assertNotFound();
        $this->assertDatabaseHas('habits', ['id' => $habit->id, 'deleted_at' => null]);
    }

    // Guest access

    /** @test */
    public function guest_does_not_access_api_endpoint()
    {
        $response = $this->getJson('/api/habits');
        $response->assertUnauthorized();
    }
}
