@extends('layouts.dashboard_app')

@section('title', 'Home')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_home.css') }}">
@endsection

@section('content')
<!-- Welcome Section -->
<div class="dashboard-header">
    <div class="welcome-section">
        <h1>Welcome, {{ Auth::user()->username ?? 'User' }}!</h1>
        <p class="motivation-text">Every step counts. Stay consistent!</p>
    </div>
</div>

<!-- Overview row -->
<div class="overview-row">
    <div class="calendar-section">
        <div class="dates-line-container">
            <div class="dates-header">
                <h2>February 2026</h2>
                <div class="dates-controls">
                    <button class="btn-today" id="todayBtn">Today</button>
                    <button class="date-nav-btn" id="prevDateBtn">‹</button>
                    <button class="date-nav-btn" id="nextDateBtn">›</button>
                </div>
            </div>
            <div class="dates-line" id="datesLine"></div>
        </div>
    </div>

    <!-- Streak counter -->
    <div class="streak-counter">
        <div class="streak-aside">
            <div class="streak-flame"><i class="fa-solid fa-fire"></i></div>
            <p class="streak-number">{{ $currentStreak }} days</p>
        </div>
        <div class="streak-week">
            <div class="week-days">
                <div class="day-container">
                    <div class="day-circle" data-day="M"></div>
                    <div class="day-letter">Mo</div>
                </div>
                <div class="day-container">
                    <div class="day-circle" data-day="T"></div>
                    <div class="day-letter">Tu</div>
                </div>
                <div class="day-container">
                    <div class="day-circle" data-day="W"></div>
                    <div class="day-letter">We</div>
                </div>
                <div class="day-container">
                    <div class="day-circle active" data-day="T"><i class="fa-solid fa-check"></i></div>
                    <div class="day-letter">Th</div>
                </div>
                <div class="day-container">
                    <div class="day-circle active" data-day="F"><i class="fa-solid fa-check"></i></div>
                    <div class="day-letter">Fr</div>
                </div>
                <div class="day-container">
                    <div class="day-circle active" data-day="S"><i class="fa-solid fa-check"></i></div>
                    <div class="day-letter">Sa</div>
                </div>
                <div class="day-container">
                    <div class="day-circle active" data-day="S"><i class="fa-solid fa-check"></i></div>
                    <div class="day-letter">Su</div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Main row -->
<div class="main-row">
    <!-- Daily habits -->
    <div class="daily-habits-section">
        <h2>Daily Habits</h2>
        <ul class="habits-list">
            <li class="habit-item">
                <div class="habit-bar">
                    <i class="fa-solid fa-dog"></i>
                    <div class="habit-info">
                        <p class="habit-name">Walk the dog</p>
                        <div class="habit-meta">
                            <span>Fitness</span>
                            <span>•</span>
                            <span>Daily</span>
                        </div>
                    </div>
                    <div class="habit-progress-container">
                        <div class="progress-stats">
                            <span class="progress-current">7,500</span>
                            <span class="progress-separator">/</span>
                            <span class="progress-target">10,000 steps</span>
                        </div>
                        <div class="progress-bar-bg">
                            <div class="progress-bar-fill" style="width: 75%"></div>
                        </div>
                    </div>
                    <div class="checkbox-wrapper-19">
                        <input type="checkbox" id="habit-1">
                        <label for="habit-1" class="check-box"></label>
                    </div>
                </div>
            </li>
            <li class="habit-item">
                <div class="habit-bar">
                    <i class="fa-solid fa-book"></i>
                    <div class="habit-info">
                        <p class="habit-name">Read a book</p>
                        <div class="habit-meta">
                            <span>Learning</span>
                            <span>•</span>
                            <span>Daily</span>
                        </div>
                    </div>
                    <div class="habit-progress-container">
                        <div class="progress-stats">
                            <span class="progress-current">20</span>
                            <span class="progress-separator">/</span>
                            <span class="progress-target">50 pages</span>
                        </div>
                        <div class="progress-bar-bg">
                            <div class="progress-bar-fill" style="width: 40%"></div>
                        </div>
                    </div>
                    <div class="checkbox-wrapper-19">
                        <input type="checkbox" id="habit-2">
                        <label for="habit-2" class="check-box"></label>
                    </div>
                </div>
            </li>
            <li class="habit-item">
                <div class="habit-bar">
                    <i class="fa-solid fa-dumbbell"></i>
                    <div class="habit-info">
                        <p class="habit-name">Workout</p>
                        <div class="habit-meta">
                            <span>Fitness</span>
                            <span>•</span>
                            <span>Daily</span>
                        </div>
                    </div>
                    <div class="habit-progress-container">
                        <div class="progress-stats">
                            <span class="progress-current">45</span>
                            <span class="progress-separator">/</span>
                            <span class="progress-target">45 minutes</span>
                        </div>
                        <div class="progress-bar-bg">
                            <div class="progress-bar-fill" style="width: 100%"></div>
                        </div>
                    </div>
                    <div class="checkbox-wrapper-19">
                        <input type="checkbox" id="habit-3" checked>
                        <label for="habit-3" class="check-box"></label>
                    </div>
                </div>
            </li>
        </ul>
    </div>

    <!-- Daily progress -->
    <div class="daily-progress-section">
        <h2>Daily Progress</h2>
        <div class="circular-progress">
            <div class="circular-progress-inner">
                <div class="progress-percentage">75%</div>
            </div>
            <svg class="progress-ring" viewBox="0 0 120 120">
                <circle class="progress-ring-bg" cx="60" cy="60" r="54" fill="none" stroke="rgba(255,255,255,0.2)" stroke-width="8"/>
                <circle class="progress-ring-fill" cx="60" cy="60" r="54" fill="none" stroke="#ff2a00" stroke-width="8" stroke-linecap="round" stroke-dasharray="339.292" stroke-dashoffset="84.823"/>
            </svg>
        </div>
    </div>
@endsection