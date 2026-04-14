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
        <button class="btn btn-add" id="addGoalButton">Add Goal</button>
        <div class="filter-container">
            <button class="filter-btn active">All</button>
            <button class="filter-btn">Completed</button>
            <button class="filter-btn">In Progress</button>
            <button class="filter-btn">Not Started</button>
        </div>
    </div>
</div>

<div class="goals-section">
    <div class="goals-grid">
        @forelse($goals as $goal)
        <div class="goal-card {{ $goal->status }}" data-id="{{ $goal->id }}">
            <div class="goal-header">
                <div class="goal-icon">
                    <i class="{{ $goal->icon }}"></i>
                </div>
                <div class="goal-details">
                    <h5>{{ $goal->title }}</h5>
                    @if($goal->description)
                        <p>{{ $goal->description }}</p>
                    @endif
                </div>
                <span class="status status-{{ str_replace(' ', '-', $goal->status) }}">{{ ucfirst(str_replace('-', ' ', $goal->status)) }}</span>
            </div>
            <div class="progress">
                <div class="progress-bar" style="width: {{ $goal->progress }}%"></div>
            </div>

            <div class="goal-data">
                <span>{{ $goal->current_value }} / {{ $goal->target_value }} {{ $goal->unit }}</span>
                @if($goal->deadline)
                    <span class="goal-deadline">Due: {{ $goal->deadline->format('M d, Y') }}</span>
                @endif
            </div>
            
            <div class="goal-actions">
                @if($goal->status !== 'completed')
                    <div class="goal-progress-actions"
                    data-goal-id="{{ $goal->id }}"
                    data-action="{{ route('goals.progress', $goal) }}">
                        <button class="btn btn-remove-progress" data-goal-id="{{ $goal->id }}">
                            <i class="fa-solid fa-minus"></i>
                        </button>
                        <input type="number" value="1" min="1">
                        <button class="btn btn-add-progress" data-goal-id="{{ $goal->id }}">
                            <i class="fa-solid fa-plus"></i>
                        </button>
                    </div>
                @endif
                <form action="{{ route('goals.destroy', $goal) }}" method="POST" onsubmit="return confirm('Delete this goal?')">
                    @csrf
                    @method('DELETE')
                    <button type="submit" class="btn btn-delete">Delete</button>
                </form>
            </div>

        </div>
        @empty
        <div id="placeholder-container"><img src="images/placeholder-img.png" alt="placeholder" id="placeholder-img"><p id="placeholder-msg">No goals yet</p></div>
        @endforelse
    </div>
</div>

<div class="goals-summary">
    <div class="summary-stats">
        <span class="stat-badge">0 Goals Completed</span>
        <span class="stat-badge">0 Goals In Progress</span>
        <span class="stat-badge">0 Goals Not Started</span>
    </div>
</div>

{{-- Add Goal Popup --}}
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
                <option value="general"  {{ old('category') == 'general'  ? 'selected' : '' }}>Nutrition</option>
                <option value="fitness"  {{ old('category') == 'fitness'  ? 'selected' : '' }}>Fitness</option>
                <option value="health"   {{ old('category') == 'health'   ? 'selected' : '' }}>Mindfulness</option>
                <option value="career"   {{ old('category') == 'career'   ? 'selected' : '' }}>Study</option>
                <option value="learning" {{ old('category') == 'learning' ? 'selected' : '' }}>Work</option>
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
                    <option value="times"   {{ old('unit') == 'times'   ? 'selected' : '' }}>Times</option>
                    <option value="days"    {{ old('unit') == 'days'    ? 'selected' : '' }}>Days</option>
                    <option value="km"      {{ old('unit') == 'km'      ? 'selected' : '' }}>Kilometers (km)</option>
                    <option value="books"   {{ old('unit') == 'books'   ? 'selected' : '' }}>Books</option>
                    <option value="minutes" {{ old('unit') == 'minutes' ? 'selected' : '' }}>Minutes</option>
                    <option value="custom"  {{ old('unit') == 'custom'  ? 'selected' : '' }}>Custom</option>
                </select>
                @error('unit')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>
        </div>

        <div class="form-group">
            <label for="deadline" class="form-label">Deadline <span style="font-weight:normal">(Optional)</span></label>
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
            <label class="form-label">Icon</label>
            <div class="icon-selector" id="icon-selector">
                <div class="selected-icon" id="selected-icon">
                    <i class="fa-solid fa-bullseye" id="selected-icon-display"></i>
                    <span>Select an icon</span>
                </div>
                <div class="icon-grid" id="icon-grid" style="display: none;"></div>
            </div>
            <input type="hidden" id="icon" name="icon" value="{{ old('icon', 'fa-solid fa-bullseye') }}">
            @error('icon')
                <div class="invalid-feedback">{{ $message }}</div>
            @enderror
        </div>

        <button type="submit" class="btn btn-add">Add Goal</button>
    </form>
</div>

@endsection
