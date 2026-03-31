@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_statistics.css') }}">
@endsection

@vite(['resources/js/statistics.js'])

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
            <div class="counter-value">{{ $current_streak }}</div>
        </div>

        <div class="counter-section">
            <div class="top-row">
                <h2>Best streak</h2>
                <i class="fa-solid fa-trophy"></i>
            </div>
            <div class="counter-value">{{ $longest_streak }}</div>
        </div>
    </div>

    <!-- Main Stats -->
    <div class="main-stats">
        <div class="daily-completions-section">
            <h2>Daily Completion Rates (Last 7 days)</h2>
            <div id="line-chart-container">
                <canvas id="daily-completions-chart" width="400" height="200"></canvas>
            </div>
        </div>
        <div class="habit-performance-section">
            <h2>Completions by Category</h2>
            <ul class="category-list">
                <li class="category-item">
                    <div class="category-bar">
                        <i class="fa-solid fa-apple-whole"></i>
                        <p>Nutrition</p>
                        <div class="progress">
                            <div class="category-progress" style="width:50%"></div>
                        </div>
                        <p>15/30</p>
                    </div>
                </li>
                <li class="category-item">
                    <div class="category-bar">
                        <i class="fa-solid fa-dumbbell"></i>
                        <p>Fitness</p>
                        <div class="progress">
                            <div class="category-progress" style="width:50%"></div>
                        </div>
                        <p>15/30</p>
                    </div>
                </li>
                <li class="category-item">
                    <div class="category-bar">
                        <i class="fa-solid fa-brain"></i>
                        <p>Mindfullness</p>
                        <div class="progress">
                            <div class="category-progress" style="width:50%"></div>
                        </div>
                        <p>15/30</p>
                    </div>
                </li>
                <li class="category-item">
                    <div class="category-bar">
                        <i class="fa-solid fa-book"></i>
                        <p>Study</p>
                        <div class="progress">
                            <div class="category-progress" style="width:50%"></div>
                        </div>
                        <p>15/30</p>
                    </div>
                </li>
                <li class="category-item">
                    <div class="category-bar">
                        <i class="fa-solid fa-briefcase"></i>
                        <p>Work</p>
                        <div class="progress">
                            <div class="category-progress" style="width:50%"></div>
                        </div>
                        <p>15/30</p>
                    </div>
                </li>
            </ul>
        </div>
    </div>

@endsection