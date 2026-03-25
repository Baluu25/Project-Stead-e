<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('achievements', function (Blueprint $table) {
           $table->id();
            $table->foreignId('user_id')->constrained()->onDelete('cascade');
            $table->string('name', 255);
            $table->text('description');
            $table->bigInteger('threshold_value')->default(0);
            $table->string('icon', 255)->nullable();
            $table->enum('achievement_type', ['Streaks', 'Milestones', 'Nutrition', 'Fitness', 'Mindfulness', 'Study', 'Work'])->default('Milestones');
            $table->Integer('progress')->default(0);
            $table->timestamp('unlocked_at')->nullable();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('achievements');
    }
};
