@extends('layouts.dashboard_app')

@section('dashboard-styles')
<link rel="stylesheet" href="{{ asset('storage/css/dashboard_achievements.css') }}">
@endsection

@section('title', 'Achievements')

@section('content')

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

    <!-- Streaks -->
    <div class="category-section">
        <h2 class="title">Streaks</h2>
        <div class="achievement-blocks">

            <div class="achievement-block locked">
                <div class="achievement-icon">
                    <img src="{{ asset('images/achievements/streak_1.png') }}" alt="First Step">
                </div>
                <div class="achievement-content">
                    <h3>First step</h3>
                    <p>Complete your first day</p>
                    <div class="progress">
                        <div class="progress-bar achievement-progress" style="width:0%"></div>
                    </div>
                    <p>0% complete</p>
                </div>
            </div>

            <div class="achievement-block locked">
                <div class="achievement-icon">
                    <img src="{{ asset('images/achievements/streak_3.png') }}" alt="Getting Warmed Up">
                </div>
                <div class="achievement-content">
                    <h3>Getting Warmed Up</h3>
                    <p>Keep your habit alive for 3 days straight.</p>
                    <div class="progress">
                        <div class="progress-bar achievement-progress" style="width:0%"></div>
                    </div>
                    <p>0% complete</p>
                </div>
            </div>

            <div class="achievement-block locked">
                <div class="achievement-icon">
                    <img src="{{ asset('images/achievements/streak_7.png') }}" alt="Locked In">
                </div>
                <div class="achievement-content">
                    <h3>Locked In</h3>
                    <p>Reach a 7-day streak</p>
                    <div class="progress">
                        <div class="progress-bar achievement-progress" style="width:0%"></div>
                    </div>
                    <p>0% complete</p>
                </div>
            </div>

        </div>
    </div>

    <!-- Milestones -->
    <div class="category-section">
        <h2 class="title">Milestones</h2>
        <div class="achievement-blocks">

            @foreach([
                ['Habit Formed','Complete a habit 10 times total'],
                ['On a Roll','Complete 50 habits total'],
                ['Century Club','Complete 100 habits total']
            ] as $item)
            <div class="achievement-block locked">
                <div class="achievement-icon">
                    <img src="{{ asset('images/achievements/placeholder-icon.png') }}" alt="{{ $item[0] }}">
                </div>
                <div class="achievement-content">
                    <h3>{{ $item[0] }}</h3>
                    <p>{{ $item[1] }}</p>
                    <div class="progress">
                        <div class="progress-bar achievement-progress" style="width:0%"></div>
                    </div>
                    <p>0% complete</p>
                </div>
            </div>
            @endforeach

        </div>
    </div>

    <!-- Nutrition -->
    <div class="category-section">
        <h2 class="title">Nutrition</h2>
        <div class="achievement-blocks">

            @foreach([
                ['First Bite','Log your first nutrition habit'],
                ['Hydration Hero','Log water intake 7 days in a row'],
                ['Clean Plate','Track all meals for 5 full days']
            ] as $item)
            <div class="achievement-block locked">
                <div class="achievement-icon">
                    <img src="{{ asset('images/achievements/placeholder-icon.png') }}" alt="{{ $item[0] }}">
                </div>
                <div class="achievement-content">
                    <h3>{{ $item[0] }}</h3>
                    <p>{{ $item[1] }}</p>
                    <div class="progress">
                        <div class="progress-bar achievement-progress" style="width:0%"></div>
                    </div>
                    <p>0% complete</p>
                </div>
            </div>
            @endforeach

        </div>
    </div>

    <!-- Fitness -->
    <div class="category-section">
        <h2 class="title">Fitness</h2>
        <div class="achievement-blocks">

            @foreach([
                ['First Sweat','Complete your first workout habit'],
                ['Weekly Warrior','Work out 3 times in one week'],
                ['Iron Will','Complete 30 fitness habits']
            ] as $item)
            <div class="achievement-block locked">
                <div class="achievement-icon">
                    <img src="{{ asset('images/achievements/placeholder-icon.png') }}" alt="{{ $item[0] }}">
                </div>
                <div class="achievement-content">
                    <h3>{{ $item[0] }}</h3>
                    <p>{{ $item[1] }}</p>
                    <div class="progress">
                        <div class="progress-bar achievement-progress" style="width:0%"></div>
                    </div>
                    <p>0% complete</p>
                </div>
            </div>
            @endforeach

        </div>
    </div>

    <!-- Mindfulness -->
    <div class="category-section">
        <h2 class="title">Mindfulness</h2>
        <div class="achievement-blocks">

            @foreach([
                ['First Breath','Complete your first mindfulness session'],
                ['Finding Peace','Complete 3 mindfulness sessions'],
                ['Zen Master','Complete 7 mindfulness sessions']
            ] as $item)
            <div class="achievement-block locked">
                <div class="achievement-icon">
                    <img src="{{ asset('images/achievements/placeholder-icon.png') }}" alt="{{ $item[0] }}">
                </div>
                <div class="achievement-content">
                    <h3>{{ $item[0] }}</h3>
                    <p>{{ $item[1] }}</p>
                    <div class="progress">
                        <div class="progress-bar achievement-progress" style="width:0%"></div>
                    </div>
                    <p>0% complete</p>
                </div>
            </div>
            @endforeach

        </div>
    </div>

    <!-- Study -->
    <div class="category-section">
        <h2 class="title">Study</h2>
        <div class="achievement-blocks">

            @foreach([
                ['First Focus','Log your first study session'],
                ['Study Streak','Study 5 days in a row'],
                ['Consistency Builder','Study 10 total hours']
            ] as $item)
            <div class="achievement-block locked">
                <div class="achievement-icon">
                    <img src="{{ asset('images/achievements/placeholder-icon.png') }}" alt="{{ $item[0] }}">
                </div>
                <div class="achievement-content">
                    <h3>{{ $item[0] }}</h3>
                    <p>{{ $item[1] }}</p>
                    <div class="progress">
                        <div class="progress-bar achievement-progress" style="width:0%"></div>
                    </div>
                    <p>0% complete</p>
                </div>
            </div>
            @endforeach

        </div>
    </div>

    <!-- Work -->
    <div class="category-section">
        <h2 class="title">Work</h2>
        <div class="achievement-blocks">

            @foreach([
                ['First Task','Complete your first work task'],
                ['Getting Productive','Complete 5 tasks'],
                ['Work Warrior','Complete 10 tasks']
            ] as $item)
            <div class="achievement-block locked">
                <div class="achievement-icon">
                    <img src="{{ asset('images/achievements/placeholder-icon.png') }}" alt="{{ $item[0] }}">
                </div>
                <div class="achievement-content">
                    <h3>{{ $item[0] }}</h3>
                    <p>{{ $item[1] }}</p>
                    <div class="progress">
                        <div class="progress-bar achievement-progress" style="width:0%"></div>
                    </div>
                    <p>0% complete</p>
                </div>
            </div>
            @endforeach

        </div>
    </div>

</div>

@endsection