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
        Schema::create('challenges', function (Blueprint $table) {
            $table->id();
            $table->foreignId('created_by_id')->constrained('users')->onDelete('cascade');
            $table->string('name', 255);
            $table->text('description');
            $table->string('icon', 255)->nullable();
            $table->enum('challenge_type', ['individual', 'team', 'competition'])->default('individual');
            $table->bigInteger('target_value');
            $table->dateTime('start_date');
            $table->dateTime('end_date');
            $table->boolean('is_active')->default(true);
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('challenges');
    }
};
