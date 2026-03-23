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
                            <div class="habit-bar"></div>
                        </li>
                        <li class="habit-item">
                            <div class="habit-bar"></div>
                        </li>
                        <li class="habit-item">
                            <div class="habit-bar"></div>
                        </li>
                        <li class="habit-item">
                            <div class="habit-bar"></div>
                        </li>
                        <li class="habit-item">
                            <div class="habit-bar"></div>
                        </li>
                    </ul>
                </div>

                <div class="daily-progress-section">
                    <h2>Daily Progress</h2>

                </div>
            </div>
@endsection