@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_goals.css') }}">
@endsection

@section('title', 'Goals')

@section('content')
<div class="goals-header">
    <div class="welcome-section">
        <h1>Goals</h1>
    </div>

    <div class="controls-section">
    <button class="btn btn-add" id="addGoalButton">Add goal</button>
    <div class="filter-container">
        <button class="filter-btn active">All</button>
        <button class="filter-btn">Completed</button>
        <button class="filter-btn">In Progress</button>
    </div>
</div>
</div>

<div class="goals-section">
    <div class="goals-grid">
        <div class="goal-card">
            <div class="goal-header">
                <i class="fa-solid fa-person-running"></i>
                <h3>Run a 5K</h3>
                <div class="goal-percentage">65%</div>
            </div>
            <div class="goal-progress-bar-container">
                <div class="progress-bar-bg">
                    <div class="progress-bar-fill" style="width: 65%"></div>
                </div>
            </div>
            <div class="goal-details">
                <div class="goal-progress-row">
                    <span class="status status-in-progress"><i class="fa-solid fa-spinner"></i> Status: In progress</span>
                    <div class="goal-progress-text">3.2km / 5km reached</div>
                </div>
                <div class="goal-habits">
                    <span><i class="fa-solid fa-person-walking-arrow-right"></i> Habits: Run (x3 wk)</span>
                    <span><i class="fa-regular fa-calendar"></i> Target: Aug 1st</span>
                </div>
            </div>
        </div>

        <div class="goal-card">
            <div class="goal-header">
                <i class="fa-brands fa-python"></i>
                <h3>Master Python</h3>
                <div class="goal-percentage">100%</div>
            </div>
            <div class="goal-progress-bar-container">
                <div class="progress-bar-bg">
                    <div class="progress-bar-fill" style="width: 100%"></div>
                </div>
            </div>
            <div class="goal-details">
                <div class="goal-progress-row">
                    <span class="status status-completed"><i class="fa-solid fa-check"></i> Status: Completed</span>
                    <div class="goal-progress-text">40 / 40 lessons completed</div>
                </div>
                <div class="goal-habits">
                    <span><i class="fa-solid fa-laptop-code"></i> Habits: Study Python (daily)</span>
                    <span><i class="fa-regular fa-calendar"></i> Target: Oct 15th</span>
                </div>
            </div>
        </div>

        <div class="goal-card">
            <div class="goal-header">
                <i class="fa-solid fa-book"></i>
                <h3>Read 12 Books</h3>
                <div class="goal-percentage">0%</div>
            </div>
            <div class="goal-progress-bar-container">
                <div class="progress-bar-bg">
                    <div class="progress-bar-fill" style="width: 0%"></div>
                </div>
            </div>
            <div class="goal-details">
                <div class="goal-progress-row">
                    <span class="status status-not-started"><i class="fa-solid fa-ban"></i> Status: Not Started</span>
                    <div class="goal-progress-text">0 / 12 books read</div>
                </div>
                <div class="goal-habits">
                    <span><i class="fa-solid fa-book-open"></i> Habits: Read 20 min (daily)</span>
                    <span><i class="fa-regular fa-calendar"></i> Target: Dec 31st</span>
                </div>
            </div>
        </div>

        <div class="goal-card">
            <div class="goal-header">
                <i class="fa-solid fa-spa"></i>
                <h3>30-Day Meditation Streak</h3>
                <div class="goal-percentage">60%</div>
            </div>
            <div class="goal-progress-bar-container">
                <div class="progress-bar-bg">
                    <div class="progress-bar-fill" style="width: 60%"></div>
                </div>
            </div>
            <div class="goal-details">
                <div class="goal-progress-row">
                    <span class="status status-in-progress"><i class="fa-solid fa-spinner"></i> Status: In Progress</span>
                    <div class="goal-progress-text">18/30 days</div>
                </div>
                <div class="goal-habits">
                    <span><i class="fa-solid fa-brain"></i> Habits: Meditate (daily)</span>
                    <span><i class="fa-regular fa-calendar"></i> Target: 30 days</span>
                </div>
            </div>
        </div>
    </div>

    <div class="goals-summary">
        <div class="summary-stats">
            <span class="stat-badge">4 Goals Active</span>
            <span class="stat-badge">1 Goals Completed</span>
            <span class="stat-badge">2 Goals In Progress</span>
            <span class="stat-badge">1 Goals Not Started</span>
        </div>
    </div>
</div>
@endsection