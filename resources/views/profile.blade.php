@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/profile.css') }}">
    <script src="{{ asset('storage/js/profile.js') }}" defer></script>
@endsection

@section('title', 'Profile')

@section('content')
<div class="dashboard-header">
    <div class="welcome-section">
        <h1>Profile</h1>
        <p class="motivation-text">Manage your personal information and preferences</p>
    </div>
</div>

<div class="profile-settings-container">
    <form action="{{ route('profile.update') }}" method="POST" enctype="multipart/form-data" class="profile-form">
        @csrf
        @method('PUT')
        
        <!-- Profile Header with Picture and Basic Info -->
        <div class="profile-header">
            <div class="profile-picture-section">
                <div class="profile-picture-container">
                    <img src="{{ Auth::user()->profile_picture ? asset('storage/' . Auth::user()->profile_picture) : asset('images/default_profile.png') }}" 
                    alt="Profile Picture" 
                    class="profile-picture"
                    id="profilePicturePreview"
                    data-default="{{ asset('images/default-avatar.png') }}">

                    <div class="profile-picture-overlay">
                        <i class="fas fa-camera"></i>
                    </div>
                </div>
                <div class="profile-picture-actions">
                    <label for="profile_picture" class="btn-change-picture">
                        <i class="fas fa-upload"></i> Change
                    </label>
                    <input type="file" name="profile_picture" id="profile_picture" accept="image/*" style="display: none;">
                    @if(Auth::user()->profile_picture)
                        <button type="button" class="btn-remove-picture" id="removePicture">
                            <i class="fas fa-trash"></i> Remove
                        </button>
                    @endif
                </div>
                <p class="picture-hint">JPG, PNG, GIF. Max 2MB</p>
            </div>
            
            <div class="profile-basic-info">
                <div class="basic-info-field">
                    <label>Name</label>
                    <input type="text" 
                           class="form-control @error('name') is-invalid @enderror" 
                           name="name" 
                           value="{{ old('name', Auth::user()->name) }}" 
                           required>
                    @error('name')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
                <div class="basic-info-field">
                    <label>Username</label>
                    <input type="text" 
                           class="form-control @error('username') is-invalid @enderror" 
                           name="username" 
                           value="{{ old('username', Auth::user()->username) }}" 
                           required>
                    @error('username')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
                <div class="basic-info-field">
                    <label>Email</label>
                    <input type="email" 
                           class="form-control @error('email') is-invalid @enderror" 
                           name="email" 
                           value="{{ old('email', Auth::user()->email) }}" 
                           required>
                    @error('email')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
            </div>
        </div>

        <!-- Personal Information Section -->
        <div class="form-section">
            <h2 class="section-title">Personal Information</h2>
            
            <div class="form-grid">
                <!-- Gender -->
                <div class="form-group">
                    <label for="gender">Gender</label>
                    <select class="form-control @error('gender') is-invalid @enderror" id="gender" name="gender">
                        <option value="">Select Gender</option>
                        <option value="male" {{ old('gender', Auth::user()->gender) == 'male' ? 'selected' : '' }}>Male</option>
                        <option value="female" {{ old('gender', Auth::user()->gender) == 'female' ? 'selected' : '' }}>Female</option>
                        <option value="other" {{ old('gender', Auth::user()->gender) == 'other' ? 'selected' : '' }}>Other</option>
                        <option value="prefer_not_to_say" {{ old('gender', Auth::user()->gender) == 'prefer_not_to_say' ? 'selected' : '' }}>Prefer not to say</option>
                    </select>
                    @error('gender')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <!-- Birthdate -->
                <div class="form-group">
                    <label for="birthdate">Birthdate</label>
                    <input type="date" 
                           class="form-control @error('birthdate') is-invalid @enderror" 
                           id="birthdate" 
                           name="birthdate" 
                           value="{{ old('birthdate', Auth::user()->birthdate) }}">
                    @error('birthdate')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <!-- Weight -->
                <div class="form-group">
                    <label for="weight">Weight (kg)</label>
                    <input type="number" 
                           step="0.1" 
                           class="form-control @error('weight') is-invalid @enderror" 
                           id="weight" 
                           name="weight" 
                           value="{{ old('weight', Auth::user()->weight) }}"
                           placeholder="70.5">
                    @error('weight')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <!-- Height -->
                <div class="form-group">
                    <label for="height">Height (cm)</label>
                    <input type="number" 
                           step="0.1" 
                           class="form-control @error('height') is-invalid @enderror" 
                           id="height" 
                           name="height" 
                           value="{{ old('height', Auth::user()->height) }}"
                           placeholder="175">
                    @error('height')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
            </div>
        </div>

        <!-- Preferences Section -->
        <div class="form-section">
            <h2 class="section-title">Preferences</h2>
            
            <div class="form-grid">
                <!-- Sleep Time -->
                <div class="form-group">
                    <label for="sleep_time">Sleep Time</label>
                    <input type="time" 
                           class="form-control @error('sleep_time') is-invalid @enderror" 
                           id="sleep_time" 
                           name="sleep_time" 
                           value="{{ old('sleep_time', Auth::user()->sleep_time) }}">
                    @error('sleep_time')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <!-- Wake Time -->
                <div class="form-group">
                    <label for="wake_time">Wake Time</label>
                    <input type="time" 
                           class="form-control @error('wake_time') is-invalid @enderror" 
                           id="wake_time" 
                           name="wake_time" 
                           value="{{ old('wake_time', Auth::user()->wake_time) }}">
                    @error('wake_time')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <!-- User Goal -->
                <div class="form-group full-width">
                    <label for="user_goal">Fitness Goal</label>
                    <select class="form-control @error('user_goal') is-invalid @enderror" id="user_goal" name="user_goal">
                        <option value="">Select Your Goal</option>
                        <option value="weight_loss" {{ old('user_goal', Auth::user()->user_goal) == 'weight_loss' ? 'selected' : '' }}>
                            Weight Loss
                        </option>
                        <option value="consistency" {{ old('user_goal', Auth::user()->user_goal) == 'consistency' ? 'selected' : '' }}>
                            Consistency
                        </option>
                        <option value="quit_habit" {{ old('user_goal', Auth::user()->user_goal) == 'quit_habit' ? 'selected' : '' }}>
                            Quit Habit
                        </option>
                        <option value="explore" {{ old('user_goal', Auth::user()->user_goal) == 'explore' ? 'selected' : '' }}>
                            Explore
                        </option>
                    </select>
                    @error('user_goal')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
            </div>
        </div>

        <!-- Action Buttons -->
        <div class="form-actions">
            <button type="submit" class="btn-save-profile">
                <i class="fas fa-save"></i> Save Changes
            </button>
            <a href="{{ route('home') }}" class="btn-cancel">
                <i class="fas fa-times"></i> Cancel
            </a>
        </div>
    </form>
</div>
@endsection