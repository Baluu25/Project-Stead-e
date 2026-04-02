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
        Schema::create('goals', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->onDelete('cascade');
            $table->string('title');
            $table->text('description')->nullable();
            $table->string('icon')->default('fa-bullseye');
            $table->string('category')->default('');
            $table->integer('target_value');
            $table->integer('current_value')->default(0);
            $table->enum('unit', ['days', 'times', 'km', 'books', 'minutes', 'custom'])->default('times');
            $table->date('deadline')->nullable();
            $table->enum('status', ['in-progress', 'completed', 'not-started'])->default('not-started');
            $table->timestamps();
});
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('goals');
    }
};
