<?php

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
    Route::get('/dashboard', function () {
        return view('home');
    })->name('dashboard');
    
    Route::get('/profile', function () {
        return view('profile');
    })->name('profile.edit');
});

Route::get('/logout', function () {
    Auth::logout();
    request()->session()->invalidate();
    request()->session()->regenerateToken();
    return redirect('/');
})->name('logout');