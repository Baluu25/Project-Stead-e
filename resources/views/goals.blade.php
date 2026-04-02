@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_goals.css') }}">
    <script src="{{ asset('storage/js/goals.js') }}" defer></script>
    <meta name="csrf-token" content="{{ csrf_token() }}">
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
        @foreach($goals as $goal)
        <div class="goal-card {{ $goal->status }}" data-id="{{ $goal->id }}">
            <div class="goal-icon"><i class="{{ $goal->icon }}"></i></div>
            <h5>{{ $goal->title }}</h5>
            <p>{{ $goal->description }}</p>
            <div class="progress">
                <div class="progress-bar" style="width: {{ $goal->progress }}%"></div>
            </div>
            <span>{{ $goal->current_value }} / {{ $goal->target_value }} {{ $goal->unit }}</span>
            <span class="status-{{ str_replace(' ', '-', $goal->status) }}">{{ ucfirst($goal->status) }}</span>

            <form action="{{ route('goals.progress', $goal) }}" method="POST">
                @csrf 
                <button type="submit">+1 Progress</button>
            </form>
        </div>
        @endforeach
    </div>
</div>

    <div class="goals-summary">
        <div class="summary-stats">
            <span class="stat-badge">{{ 0 }} Goals Completed</span>
            <span class="stat-badge">{{ 0 }} Goals In Progress</span>
            <span class="stat-badge">{{ 0 }} Goals Not Started</span>
        </div>
    </div>

    <div class="goal-form-card" id="goalFormPopup" style="display: none;"> 
    <div class="goal-form-header">
        <h1 class="goal-form-title">Add Goal</h1>
        <button type="button" class="close-goal-form" id="closePopupBtn">&times;</button>
    </div>
    <p class="goal-form-subtitle">Add a new goal to track your progress</p>

    <form method="POST" action="{{ route('goals.store') }}" id="goal-form">
        @csrf
    
        <div class="form-group">
            <label for="title" class="form-label">Title</label>
            <input type="text" 
               id="title" 
               name="title" 
               class="form-control @error('title') is-invalid @enderror" 
               value="{{ old('title') }}" 
               required 
               autofocus>
            @error('title')
                <div class="invalid-feedback">{{ $message }}</div>
            @enderror
        </div>

        <div class="form-group">
            <label for="description" class="form-label">Description</label>
            <textarea id="description" 
                  name="description" 
                  rows="3" 
                  class="form-control @error('description') is-invalid @enderror">{{ old('description') }}</textarea>
            @error('description')
                <div class="invalid-feedback">{{ $message }}</div>
            @enderror
        </div>

        <div class="form-group">
            <label for="category" class="form-label">Category</label>
            <select id="category" 
                name="category" 
                class="form-control @error('category') is-invalid @enderror" 
                required>
                <option value="">Select a category</option>
                <option value="general" {{ old('category') == 'nutrition' ? 'selected' : '' }}>Nutrition</option>
                <option value="fitness" {{ old('category') == 'fitness' ? 'selected' : '' }}>Fitness</option>
                <option value="health" {{ old('category') == 'mindfullness' ? 'selected' : '' }}>Mindfullness</option>
                <option value="career" {{ old('category') == 'study' ? 'selected' : '' }}>Study</option>
                <option value="learning" {{ old('category') == 'work' ? 'selected' : '' }}>Work</option>
            </select>
            @error('category')
                <div class="invalid-feedback">{{ $message }}</div>
            @enderror
        </div>

        <div class="form-row">
            <div class="form-group half">
                <label for="target_value" class="form-label">Target Value</label>
                <input type="number" 
                   id="target_value" 
                   name="target_value" 
                   class="form-control @error('target_value') is-invalid @enderror" 
                   value="{{ old('target_value', 1) }}" 
                   min="1"
                   required>
                @error('target_value')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>

            <div class="form-group half">
                <label for="unit" class="form-label">Unit</label>
                <select id="unit" 
                    name="unit" 
                    class="form-control @error('unit') is-invalid @enderror" 
                    required>
                    <option value="times" {{ old('unit') == 'times' ? 'selected' : '' }}>Times</option>
                    <option value="days" {{ old('unit') == 'days' ? 'selected' : '' }}>Days</option>
                    <option value="km" {{ old('unit') == 'km' ? 'selected' : '' }}>Kilometers (km)</option>
                    <option value="books" {{ old('unit') == 'books' ? 'selected' : '' }}>Books</option>
                    <option value="minutes" {{ old('unit') == 'minutes' ? 'selected' : '' }}>Minutes</option>
                    <option value="custom" {{ old('unit') == 'custom' ? 'selected' : '' }}>Custom</option>
                </select>
                @error('unit')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>
        </div>

        <div class="form-group">
            <label for="deadline" class="form-label">Deadline (Optional)</label>
            <input type="date" 
               id="deadline" 
               name="deadline" 
               class="form-control @error('deadline') is-invalid @enderror" 
               value="{{ old('deadline') }}">
            @error('deadline')
                <div class="invalid-feedback">{{ $message }}</div>
            @enderror
        </div>

        <div class="form-group">
            <label for="icon" class="form-label">Icon</label>
            <div class="icon-selector" id="icon-selector">
                <div class="selected-icon" id="selected-icon">
                    <i class="fa-solid fa-bullseye" id="selected-icon-display"></i>
                    <span>Select an icon</span>
                </div>
                <div class="icon-grid" id="icon-grid" style="display: none;">

                </div>
            </div>
            <input type="hidden" id="icon" name="icon" value="{{ old('icon', 'fa-solid fa-bullseye') }}">
            @error('icon')
                <div class="invalid-feedback">{{ $message }}</div>
            @enderror
        </div>

        <button type="submit" class="btn btn-add">Add Goal</button>
    </form>
</div>
</div>
@endsection