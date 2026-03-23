@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_statistics.css') }}">
@endsection

@section('title', 'Statistics')
@section('content')
    <!-- Welcome Section -->
    <div class="dashboard-header">
        <div class="welcome-section">
            <h1>Statistics</h1>
        </div>
    </div>

    <!-- Counter widgets -->
    <div class="counters-row">
        <div class="counter-section">
            <div class="top-row">
                <h2>Total habits tracked</h2>
                <i class="fa-solid fa-hashtag"></i>
            </div>
            <div class="counter-value">{{ $total_habits }}</div>
        </div>

        <div class="counter-section">
            <div class="top-row">
                <h2>Active habits</h2>
                <i class="fa-solid fa-check-circle"></i>
            </div>
            <div class="counter-value">{{ $active_habits }}</div>
        </div>

        <div class="counter-section">
            <div class="top-row">
                <h2>Current streak</h2>
                <i class="fa-solid fa-fire"></i>
            </div>
            <div class="counter-value">7</div>
        </div>

        <div class="counter-section">
            <div class="top-row">
                <h2>Best streak</h2>
                <i class="fa-solid fa-trophy"></i>
            </div>
            <div class="counter-value">30</div>
        </div>
    </div>

    <!-- Main Stats -->
    <div class="main-stats">
        <div class="daily-completions-section">
            <h2>Daily Completion Rates (Last 30 days)</h2>
        </div>
        <div class="habit-performance-section">
            <h2>Habit Performance Overview</h2>
        </div>
    </div>

@endsection