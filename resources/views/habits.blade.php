@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_habits.css') }}">
@endsection

@section('title', 'Habits')

@section('content')
    <!-- Title -->
     <div class="dashboard-header">
        <div class="welcome-section">
            <h1>Manage habits</h1>
        </div>
    </div>
    <div class="input-section">
        <input type="text" placeholder="Enter habit name..." class="habit-input">
        <button class="btn btn-add">Add habit</button>
    </div>
    <div class="habits-section">
        <div class="habits-header">
            <span></span>
            <span>Name</span>
            <span>Frequency</span>
            <span>Status</span>
            <span>Actions</span>
        </div>
        <div class="habit-item">
            <span class="habit-icon">💧</span>
            <span>Drink Water</span>
            <span>Daily</span>
            <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="switchCheckDefault">
            </div>
            <span>
                <button class="icon-btn">
                    <i class="fa-solid fa-pen-to-square"></i>
                </button>
                <button class="icon-btn">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </span>
        </div>
        <div class="habit-item">
            <span class="habit-icon">📚</span>
            <span>Read</span>
            <span>Daily</span>
            <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="switchCheckDefault">
            </div>
            <span>
                <button class="icon-btn">
                    <i class="fa-solid fa-pen-to-square"></i>
                </button>
                <button class="icon-btn">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </span>
        </div>
        <div class="habit-item">
            <span class="habit-icon">💪</span>
            <span>Workout</span>
            <span>3x/week</span>
            <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="switchCheckDefault">
            </div>
            <span>
                <button class="icon-btn">
                    <i class="fa-solid fa-pen-to-square"></i>
                </button>
                <button class="icon-btn">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </span>
        </div>
    </div>
@endsection