@extends('layouts.dashboard_app')

@section('title', 'dashboard')

@vite(['resources/css/dashboard_home.css'])

@section('content')
<div class="dashboard-container">
    <div class="dashboard-layout">
        <aside class="dashboard-sidebar">
            <nav class="sidebar-nav">
                <div class="sidebar-header">
                    <h3>Dashboard</h3>
                </div>
                <ul class="nav-items">
                    <li class="nav-item active">
                        <a href="#">
                            <span class="nav-text">Home</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="#">
                            <span class="nav-text">Habits</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="#">
                            <span class="nav-text">Statistics</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="#">
                            <span class="nav-text">Goals</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="#">
                            <span class="nav-text">Achievements</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </aside>

        <main class="dashboard-main">
            <!-- Welcome Section -->
            <div class="dashboard-header-with-calendar">
                <div class="welcome-section">
                    <h1>Welcome, {{ Auth::user()->username ?? 'User' }}!</h1>
                    <p class="motivation-text">Every step counts. Stay consistent!</p>
                </div>
            </div>

            <!-- Main data -->
            <div class="main-data">
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

                <div class="streak-counter">
                    <div class="streak-aside">
                        <div class="streak-flame">🔥</div>
                        <p class="streak-number">7 days</p>
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
                                <div class="day-circle active" data-day="T">&#10004;</div>
                                <div class="day-letter">Th</div>
                            </div>
                            <div class="day-container">
                                <div class="day-circle active" data-day="F">&#10004;</div>
                                <div class="day-letter">Fr</div>
                            </div>
                            <div class="day-container">
                                <div class="day-circle active" data-day="S">&#10004;</div>
                                <div class="day-letter">Sa</div>
                            </div>
                            <div class="day-container">
                                <div class="day-circle active" data-day="S">&#10004;</div>
                                <div class="day-letter">Su</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Daily Habits Section - 75% width, centered below -->
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
        </main>
    </div>
</div>
@endsection