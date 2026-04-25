<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\HabitController;
use App\Http\Controllers\HabitCompletionController;
use App\Http\Controllers\StatisticsController;
use App\Http\Controllers\HomeController;
use App\Http\Controllers\GoalController;
use App\Http\Controllers\AchievementController;
use App\Http\Controllers\UserController;
use App\Http\Controllers\AdminController;

Route::post('/register', [AuthController::class, 'register']);
Route::post('/login',    [AuthController::class, 'login']);

Route::middleware('auth:sanctum')->group(function () {

    // Auth
    Route::post('/logout', [AuthController::class, 'logout']);
    Route::get('/user',    [AuthController::class, 'user']);

    // Habits
    Route::get('/habits',    [HabitController::class, 'apiIndex']);
    Route::post('/habits',   [HabitController::class, 'store']);
    Route::put('/habits/{id}',    [HabitController::class, 'update']);
    Route::delete('/habits/{id}', [HabitController::class, 'destroy']);

    // Habit Completions
    Route::post('/habit-completions',                          [HabitCompletionController::class, 'store']);
    Route::delete('/habit-completions/{habitId}/today/last',   [HabitCompletionController::class, 'destroyLast']);

    // Statistics
    Route::get('/statistics', [StatisticsController::class, 'apiIndex']);

    // Dashboard
    Route::get('/home', [HomeController::class, 'apiIndex']);

    // Goals
    Route::post('/goals',                       [GoalController::class, 'store']);
    Route::put('/goals/{goal}',                 [GoalController::class, 'update']);
    Route::post('/goals/{goal}/progress',        [GoalController::class, 'progress']);
    Route::delete('/goals/{goal}',              [GoalController::class, 'destroy']);
    Route::get('/goals',                        [GoalController::class, 'index']);

    // Achievements
    Route::get('/achievements', [AchievementController::class, 'index']);

    // User profile
    Route::get('/profile',  [UserController::class, 'show']);
    Route::put('/profile',  [UserController::class, 'update']);
    Route::post('/profile/picture', [UserController::class, 'uploadPicture']);

    Route::middleware('admin')->group(function () {
        Route::get('/admin/users',                        [AdminController::class, 'index']);
        Route::delete('/admin/users/{user}',              [AdminController::class, 'destroy']);
        Route::patch('/admin/users/{user}/promote',       [AdminController::class, 'promote']);
        Route::patch('/admin/users/{user}/demote',        [AdminController::class, 'demote']);
    });
});