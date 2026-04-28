<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('users', function (Blueprint $table) {
            $table->dropColumn(['weight', 'height', 'sleep_time', 'wake_time']);
        });
    }

    public function down(): void
    {
        Schema::table('users', function (Blueprint $table) {
            $table->decimal('weight', 5, 1)->nullable();
            $table->integer('height')->nullable();
            $table->time('sleep_time')->nullable();
            $table->time('wake_time')->nullable();
        });
    }
};
