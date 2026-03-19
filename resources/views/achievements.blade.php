@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_achievements.css') }}">
@endsection

@section('title', 'Achievements')

@section('content')
    <!-- Title -->
    <div class="achievement-header">
        <div class="welcome-section">
            <h1>Achievements</h1>
        </div>
        <div class="controls-section">
            <div class="filter-container">
                <button class="filter-btn active">All</button>
                <button class="filter-btn">Completed</button>
                <button class="filter-btn">In Progress</button>
            </div>
            <input type="text" placeholder="Search" class="achievement-input">
        </div>
    </div>
    <div class="achievements-container">
    <div class="category-section">
        <h2 class="title">Streaks</h2>
    <div class="achievement-blocks">

        <div class="achievement-block">
            <div class="achievement-icon">
                <img src="{{ asset('images/achievements/streak_1.png') }}" alt="Momentum Builder">
            </div>
            <div class="achievement-content">
                <h3>First step</h3>
                <p>Complete your first day. Every Journey starts here.</p>

                <div class="progress">
                    <div class="progress-bar achievement-progress" style="width:100%"></div>
                </div>
                <p>100% complete</p>
            </div>
        </div>

        <div class="achievement-block locked">
            <div class="achievement-icon">
                <img src="{{ asset('images/achievements/streak_3.png') }}" alt="Momentum Builder">
            </div>
            <div class="achievement-content">
                <h3>Getting Warmed Up</h3>
                <p>Keep your habit alive for 3 days straight.</p>

                <div class="progress">
                    <div class="progress-bar achievement-progress" style="width:30%"></div>
                </div>
                <p>30% complete</p>
            </div>
        </div>

        <div class="achievement-block locked">
            <div class="achievement-icon">
                <img src="{{ asset('images/achievements/streak_7.png') }}" alt="Momentum Builder">
            </div>
            <div class="achievement-content">
                <h3>Locked In</h3>
                <p>Reach a 7-day streak</p>

                <div class="progress">
                    <div class="progress-bar achievement-progress" style="width:10%"></div>
                </div>
                <p>10% complete</p>
            </div>
        </div>

    </div>
    </div>
    </div>
   
@endsection