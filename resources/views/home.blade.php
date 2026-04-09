@extends('layouts.dashboard_app')

@section('title', 'Home')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_home.css') }}">
    <script src="{{ asset("storage/js/home.js") }}" defer></script>
@endsection

@section('content')
<!-- Welcome Section -->
<div class="dashboard-header">
    <div class="welcome-section">
        <h1>Home</h1>
    </div>
</div>

<!-- Overview row -->
<div class="overview-row">
    <div class="calendar-section">
        <div class="dates-line-container">
            <div class="dates-header">
                <h2> 2026</h2>
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
            @foreach($streakDays as $day)
                <div class="day-container">
                    <div class="day-circle {{ $day['completed'] ? 'active' : '' }}">
                    @if($day['completed'])
                        <i class="fa-solid fa-check"></i>
                    @endif
                    </div>
                    <div class="day-letter">{{ $day['label'] }}</div>
                </div>
                @endforeach
            </div>
        </div>
    </div>
</div>

<!-- Main row -->
<div class="main-row">
    <div class="daily-habits-section">
    <h3>Today's Habits</h3>
    @forelse($todaysHabits as $habit)
        @php
            $completed  = $habit->completions->count();
            $target     = $habit->target_count ?? 1;
            $percent    = $target > 0 ? min(100, round(($completed / $target) * 100)) : 0;
            $isDone     = $percent >= 100;
        @endphp
        <div class="habit-item {{ $isDone ? 'completed' : '' }}">
            {{-- Icon --}}
            <div class="habit-icon">
                <i class="fa-solid fa-{{ $habit->icon ?? 'circle-check' }}"></i>
            </div>
            {{-- Name + Category --}}
            <div class="habit-info">
                <span class="habit-name">{{ $habit->name }}</span>
                <span class="habit-category badge">{{ $habit->category }}</span>
            </div>
            {{-- Progress --}}
            <div class="habit-progress">
                <div class="progress-numbers">
                    <span class="progress-current">{{ $completed }}</span>
                    <span class="progress-separator">/</span>
                    <span class="progress-target">{{ $target }} {{ $habit->unit ?? '' }}</span>
                </div>
                <div class="progress-bar-track">
                    <div class="progress-bar-fill" style="width: {{ $percent }}%"></div>
                </div>
                <div class="habit-actions">
                    <button class="btn btn-remove-progress" id="removeHabitProgressBtn"><i class="fa-solid fa-minus"></i></button>
                    <button class="btn btn-add-progress" id="addHabitProgressBtn"><i class="fa-solid fa-plus"></i></button>
                </div>
            </div>
            @if($isDone)
                <div class="habit-done-badge">
                    <i class="fa-solid fa-check"></i>
                </div>
            @endif
        </div>
    @empty
        <div class="no-habits-message">
            <p>No habits scheduled for today.</p>
            <a href="{{ route('habits') }}">Add your first habit</a>
        </div>
    @endforelse
</div>


    <!-- Daily progress -->
    <div class="daily-progress-section">
        <h2>Daily Progress</h2>
        <div class="circular-progress">
            <div class="circular-progress-inner">
            @php
                $circumference = 2 * pi() * 54;
                $offset = $circumference - ($dailyProgressPercent / 100) * $circumference;
            @endphp
            <svg class="progress-ring" width="140" height="140" viewBox="0 0 140 140">
                <circle class="progress-ring-bg" cx="70" cy="70" r="54" fill="none" stroke="rgba(255,255,255,0.2)" stroke-width="8"/>
                <circle class="progress-ring-fill" cx="70" cy="70" r="54" fill="none" stroke="#ff2a00" stroke-width="8" stroke-linecap="round" stroke-dasharray="{{ $circumference }}" stroke-dashoffset="{{ $offset }}"/>
            </svg>
            <span class="progress-percentage">{{ $dailyProgressPercent }}%</span>
            </div>
        </div>
    </div>
@endsection