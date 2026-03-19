<?php

use App\Http\Controllers\HabitCompletionController;
use App\Http\Controllers\HabitController;
use App\Http\Controllers\HomeController;
use App\Http\Controllers\StatisticsController;
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
// Protected routes (requires login)
Route::middleware(['auth'])->group(function () {
    // Dashboard
    Route::get('/home', [HomeController::class, 'index'])->name('home');
    
    // Profile
    Route::get('/profile', function () {
        return view('profile');
    })->name('profile.edit');
    
    // Habits
    Route::resource('habits', HabitController::class)
        ->except(['create', 'edit', 'show']);

    Route::post('/habit-completions', [HabitCompletionController::class, 'store'])
        ->name('habit-completions.store');
    
    // Statistics
    Route::get('/statistics', [StatisticsController::class, 'index'])->name('statistics');
    
    // Goals
    Route::get('/goals', function () {
        return view('goals');
    })->name('goals');
    
    // Achievements
    Route::get('/achievements', function () {
        return view('achievements');
    })->name('achievements');
});

Route::get('/logout', function () {
    Auth::logout();
    request()->session()->invalidate();
    request()->session()->regenerateToken();
    return redirect('/');
})->name('logout');