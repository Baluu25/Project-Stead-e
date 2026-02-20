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
        Schema::create('habits', function (Blueprint $table) {
           $table->id();
            $table->unsignedBigInteger('user_id');
            $table->string('name');
            $table->text('description');
            $table->string('category');
            $table->enum('frequency', ['daily', 'weekly', 'monthly', 'custom']);
            $table->bigInteger('target_count')->default(1);
            $table->string('color')->default('#3B82F6');
            $table->string('icon')->default('star');
            $table->boolean('is_active')->default(true);
            $table->boolean('is_public')->default(false);
            $table->dateTime('created_at');
            $table->dateTime('updated_at');
            $table->foreign('user_id')
                  ->references('id')
                  ->on('Users')
                  ->onDelete('cascade');
            $table->softDeletes();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('habits');
    }
};
