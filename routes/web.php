<?php

use App\Http\Controllers\HabitController;
use App\Http\Controllers\HomeController;
use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    return view('welcome');
});

Auth::routes();

Route::get('/index', function () {
    return view('index');
})->name('index');

Route::get('/home', function () {
    return redirect()->route('dashboard');
})->middleware(['auth']);

// Protected routes (requires login)
Route::middleware(['auth'])->group(function () {
    // Dashboard
    Route::get('/home', [HomeController::class, 'index'])->name('home');
    
    // Profile
    Route::get('/profile', function () {
        return view('profile');
    })->name('profile.edit');
    
    // Habits
    Route::get('/habits', [HabitController::class, 'index'])->name('habits');
    
    // Statistics
    Route::get('/statistics', function () {
        return view('statistics');
    })->name('statistics');
    
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