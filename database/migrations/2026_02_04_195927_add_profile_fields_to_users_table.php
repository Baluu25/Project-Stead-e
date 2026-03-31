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
        Schema::table('users', function (Blueprint $table) {
            $table->string('username')->unique()->after('name');
            $table->enum('gender', ['male', 'female', 'other'])->nullable()->after('email');
            $table->date('birthdate')->nullable()->after('gender');
            $table->decimal('weight', 5, 1)->nullable()->after('birthdate');
            $table->integer('height')->nullable()->after('weight');
            $table->time('sleep_time')->nullable()->after('height');
            $table->time('wake_time')->nullable()->after('sleep_time');
            $table->enum('user_goal', ['weight_loss', 'consistency', 'quit_habit', 'explore'])->nullable()->after('wake_time');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('users', function (Blueprint $table) {
            $table->dropColumn(['username', 'gender', 'birthdate', 'weight', 'height', 'sleep_time', 'wake_time', 'user_goal']);
        });
    }
};
