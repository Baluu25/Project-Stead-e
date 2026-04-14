@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_habits.css') }}">
    <script src="{{ asset('storage/js/habits.js') }}" defer></script>
    <script>
      window.userGoals = @json($goals->map(fn($g) => ['id' => $g->id, 'title' => $g->title]));
    </script>
    <meta name="csrf-token" content="{{ csrf_token() }}">
@endsection

@section('title', 'Habits')

@section('content')
    <div class="dashboard-header">
        <div class="welcome-section">
            <h1>Manage habits</h1>
        </div>
    </div>

    <div class="input-section">
        <input type="text" id="habit-name" placeholder="Search habits..." class="habit-input">
        <button class="btn btn-add" id="addHabitBtn">Add habit</button>
    </div>

    <div class="habit-form-card" id="habitFormPopup" style="display: none;"> 
        <div class="habit-form-header">
            <h1 class="habit-form-title" id="habitFormTitle">Add habit</h1>
            <button type="button" class="close-habit-form" id="closePopupBtn">&times;</button>
        </div>
        <p class="habit-form-subtitle">Add a habit to your profile</p>

        <form method="POST" action="loadHabits()" id="habit-form">
            @csrf
            <input type="hidden" id="habit-id" name="habit_id" value="">
        
            <div class="form-group">
                <label for="name" class="form-label">Habit Name</label>
                <input type="text" 
                   id="name" 
                   name="name" 
                   class="form-control @error('name') is-invalid @enderror" 
                   value="{{ old('name') }}" 
                   required 
                   autofocus>
                @error('name')
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
                    <option value="Nutrition" {{ old('category') == 'Nutrition' ? 'selected' : '' }}>Nutrition</option>
                    <option value="Fitness" {{ old('category') == 'Fitness' ? 'selected' : '' }}>Fitness</option>
                    <option value="Mindfulness" {{ old('category') == 'Mindfullness' ? 'selected' : '' }}>Mindfulness</option>
                    <option value="Study" {{ old('category') == 'Study' ? 'selected' : '' }}>Study</option>
                    <option value="Work" {{ old('category') == 'Work' ? 'selected' : '' }}>Work</option>
                </select>
                @error('category')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>

            <div class="form-group">
                <label for="frequency" class="form-label">Frequency</label>
                <select id="frequency" 
                    name="frequency" 
                    class="form-control @error('frequency') is-invalid @enderror" 
                    required>
                    <option value="">Select frequency</option>
                    <option value="daily" {{ old('frequency') == 'daily' ? 'selected' : '' }}>Daily</option>
                    <option value="weekly" {{ old('frequency') == 'weekly' ? 'selected' : '' }}>Weekly</option>
                    <option value="monthly" {{ old('frequency') == 'monthly' ? 'selected' : '' }}>Monthly</option>
                </select>
                @error('frequency')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>

            <div class="form-group">
                <label for="target_count" class="form-label">Target Count</label>
                <input type="number" 
                   id="target_count" 
                   name="target_count" 
                   class="form-control @error('target_count') is-invalid @enderror" 
                   value="{{ old('target_count', 1) }}" 
                   min="1"
                   required>
                @error('target_count')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>

            <div class="form-group">
                <label for="icon" class="form-label">Icon</label>
                <div class="icon-selector" id="icon-selector">
                    <div class="selected-icon" id="selected-icon">
                        <i class="fa-solid fa-smile" id="selected-icon-display"></i>
                        <span>Select an icon</span>
                    </div>
                    <div class="icon-grid" id="icon-grid" style="display: none;">
                        <!-- Icons -->
                    </div>
                </div>
                <input type="hidden" id="icon" name="icon" value="{{ old('icon', 'fa-solid fa-smile') }}">
                @error('icon')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>
            <div class="form-group">
                <label for="goal_id" class="form-label">Add to a Goal</label>
                <select id="goal_id" name="goal_id" class="form-control">
                    <option value="">No Goal</option>
                </select>
            </div>
            <button type="submit" class="btn btn-add" id="habitFormSubmitBtn">Add Habit</button>
        </form>
    </div>

    <div class="habits-section">
        <div class="habits-header">
            <span></span>
            <span>Name</span>
            <span>Goal</span>
            <span>Frequency</span>
            <span>Target</span>
            <span>Status</span>
            <span>Actions</span>
        </div>
        <div id="habits-list">
        </div>
    </div>
@endsection