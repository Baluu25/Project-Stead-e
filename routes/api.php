<?php

use App\Http\Controllers\HabitController;
use App\Http\Controllers\HabitCompletionController;
use App\Http\Controllers\HomeController;
use App\Http\Controllers\StatisticsController;
use Illuminate\Support\Facades\Route;

Route::middleware(['web', 'auth'])->group(function () {

    // Habits API
    Route::get('/habits', [HabitController::class, 'apiIndex']);
    Route::post('/habits', [HabitController::class, 'store']);
    Route::put('/habits/{id}', [HabitController::class, 'update']);
    Route::delete('/habits/{id}', [HabitController::class, 'destroy']);

    // Habit completions
    Route::post('/habit-completions', [HabitCompletionController::class, 'store']);

    // Statistics
    Route::get('/statistics', [StatisticsController::class, 'apiIndex']);

    Route::get('/home', [HomeController::class, 'apiIndex']);

    Route::delete('/habit-completions/{habitId}/today/last', [HabitCompletionController::class, 'destroyLast']);
});