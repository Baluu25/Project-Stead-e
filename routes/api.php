<?php

use App\Http\Controllers\HabitController;
use App\Http\Controllers\HabitCompletionController;
use App\Http\Controllers\StatisticsController;
use Illuminate\Support\Facades\Route;

Route::middleware(['auth:sanctum'])->group(function () {
    // Habits API
    Route::get('/habits', [HabitController::class, 'index']);
    Route::post('/habits', [HabitController::class, 'store']);
    Route::put('/habits/{id}', [HabitController::class, 'update']);
    Route::delete('/habits/{id}', [HabitController::class, 'destroy']);

    // Habit completions
    Route::post('/habit-completions', [HabitCompletionController::class, 'store']);

    // Statistics
    Route::get('/statistics', [StatisticsController::class, 'index']);
});