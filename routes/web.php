<?php

use App\Http\Controllers\AchievementController;
use App\Http\Controllers\GoalController;
use App\Http\Controllers\HabitCompletionController;
use App\Http\Controllers\HabitController;
use App\Http\Controllers\HomeController;
use App\Http\Controllers\StatisticsController;
use App\Http\Controllers\UserController;
use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    return view('welcome');
});

Auth::routes();

Route::get('/index', function () {
    return view('index');
})->name('index');

Route::get('/contact', function () {
    return view('contact');
})->name('contact');

Route::middleware(['auth'])->group(function () {
    // Dashboard
    Route::get('/home', [HomeController::class, 'index'])->name('home');
    
    // Profile
    Route::get('/profile', [UserController::class, 'show'])->name('profile')->middleware('auth');
    Route::put('/profile', [UserController::class, 'update'])->name('profile.update')->middleware('auth');
    
    // Habits
    Route::get('/habits', [HabitController::class, 'index'])->name('habits')->middleware('auth');

    Route::post('/habit-completions', [HabitCompletionController::class, 'store'])
        ->name('habit-completions.store');
    
    // Statistics
    Route::get('/statistics', [StatisticsController::class, 'index'])->name('statistics');
    
    // Goals
    Route::get('/goals', [GoalController::class, 'index'])->name('goals');
    Route::post('/goals', [GoalController::class, 'store'])->name('goals.store');
    Route::put('/goals/{goal}', [GoalController::class, 'update'])->name('goals.update');
    Route::post('/goals/{goal}/progress', [GoalController::class, 'progress'])->name('goals.progress');
    Route::delete('/goals/{goal}', [GoalController::class, 'destroy'])->name('goals.destroy');
    
    // Achievements
    Route::get('/achievements', [AchievementController::class, 'index'])->name('achievements');
});

Route::get('/logout', function () {
    Auth::logout();
    request()->session()->invalidate();
    request()->session()->regenerateToken();
    return redirect('/');
})->name('logout');

Route::fallback(function () {
    abort(404);
});