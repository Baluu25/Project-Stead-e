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
            <canvas id="daily-completions-chart"
            width="400" height="200"
            data-completions="{{ json_encode($daily_completions) }}"></canvas>
        </div>
        </div>
        <div class="habit-performance-section">
            <h2>Completions by Category</h2>
                @php
                $categoryConfig = [
                    'Nutrition'    => 'fa-apple-whole',
                    'Fitness'      => 'fa-dumbbell',
                    'Mindfulness'  => 'fa-brain',
                    'Study'        => 'fa-book',
                    'Work'         => 'fa-briefcase',
                ];
                @endphp

            <ul class="category-list">
            @forelse($category_breakdown as $category => $count)
                @php
                    $icon    = $categoryConfig[$category] ?? 'fa-circle';
                    $percent = $total_category_completions > 0
                        ? round($count / $total_category_completions * 100)
                        : 0;
                @endphp
                <li class="category-item">
                    <div class="category-bar">
                        <i class="fa-solid {{ $icon }}"></i>
                        <p>{{ $category }}</p>
                        <div class="progress">
                            <div class="category-progress" style="width:{{ $percent }}%"></div>
                        </div>
                        <p>{{ $count }}</p>
                    </div>
                </li>
            @empty
                <li class="category-item" style="grid-column:1/-1;">
                    <p style="color:white;text-align:center;margin-top:1rem;">No completions yet.</p>
                </li>
        @endforelse
            </ul>
        </div>
    </div>

@endsection