@extends('layouts.dashboard_app')

@section('dashboard-styles')
<link rel="stylesheet" href="{{ asset('storage/css/dashboard_achievements.css') }}">
<script src="{{ asset('storage/js/achievements.js') }}" defer></script>
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

<div class="achievement-summary">
    <span>{{ $completedCount }} / {{ $totalCount }} Achievements Unlocked</span>
    <div class="summary-bar">
        <div class="summary-fill" style="width: {{ $totalCount > 0 ? round(($completedCount / $totalCount) * 100) : 0 }}%"></div>
    </div>
</div>



@forelse($achievements as $category => $categoryAchievements)
    <div class="category-section" data-category="{{ $category }}">
        <h2 class="title">{{ $category }}</h2>
        
        <div class="achievement-blocks">
            @foreach($categoryAchievements as $achievement)
                @php
                    $progress = $achievement->threshold_value > 0 
                        ? round(($achievement->progress / $achievement->threshold_value) * 100) 
                        : 0;
                    $progress = min(100, $progress);
                    
                    $isCompleted = !is_null($achievement->unlocked_at);
                    $statusClass = $isCompleted ? '' : 'locked';
                @endphp
                <div class="achievement-block {{ $statusClass }}"
                     data-name="{{ strtolower($achievement->name) }}"
                     data-status="{{ $isCompleted ? 'completed' : ($progress > 0 ? 'in-progress' : 'locked') }}">
                    
                    <div class="achievement-icon">
                        <img src="{{ asset('images/achievements/' . ($achievement->icon ?: 'placeholder-icon.png')) }}"
                        alt="{{ $achievement->name }}">
                    </div>
                    
                    <div class="achievement-content">
                        <h3>{{ $achievement->name }}</h3>
                        <p>{{ $achievement->description }}</p>
                        
                        <div class="progress">
                            <div class="progress-bar achievement-progress" 
                                 style="width:{{ $progress }}%"></div>
                        </div>
                        
                        @if($isCompleted)
                            <p class="unlocked-label">Unlocked!</p>
                        @else
                            <p>{{ $achievement->progress }} / {{ $achievement->threshold_value }}</p>
                        @endif
                    </div>
                </div>
            @endforeach
        </div>
    </div>
@empty
    <p style="color:white">No achievements yet.</p>
@endforelse

@endsection