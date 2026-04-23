@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/admin.css') }}">
@endsection

@section('title', 'Admin Panel')

@section('content')

{{-- Page header (matches statistics.blade.php pattern) --}}
<div class="dashboard-header">
    <div class="welcome-section">
        <h1>Admin Panel</h1>
        <p class="motivation-text">Manage users and monitor platform statistics</p>
    </div>
</div>

{{-- Flash messages --}}
@if(session('success'))
    <div class="admin-alert admin-alert-success">
        <i class="fa-solid fa-circle-check"></i>
        {{ session('success') }}
    </div>
@endif
@if(session('error'))
    <div class="admin-alert admin-alert-error">
        <i class="fa-solid fa-circle-exclamation"></i>
        {{ session('error') }}
    </div>
@endif

{{-- Stat counters (copy of statistics.blade.php counters-row pattern) --}}
<div class="counters-row">
    <div class="counter-section">
        <div class="top-row">
            <h2>Total Users</h2>
            <i class="fa-solid fa-users"></i>
        </div>
        <div class="counter-value">{{ $stats['total_users'] }}</div>
    </div>

    <div class="counter-section">
        <div class="top-row">
            <h2>Admin Users</h2>
            <i class="fa-solid fa-shield-halved"></i>
        </div>
        <div class="counter-value">{{ $stats['admin_count'] }}</div>
    </div>

    <div class="counter-section">
        <div class="top-row">
            <h2>Total Habits</h2>
            <i class="fa-solid fa-check-circle"></i>
        </div>
        <div class="counter-value">{{ $stats['total_habits'] }}</div>
    </div>

    <div class="counter-section">
        <div class="top-row">
            <h2>Total Goals</h2>
            <i class="fa-solid fa-bullseye"></i>
        </div>
        <div class="counter-value">{{ $stats['total_goals'] }}</div>
    </div>
</div>

{{-- Users table --}}
<div class="users-section">
    <h2 class="users-section-title">All Users</h2>

    @forelse($users as $user)
        @if($loop->first)
        <div class="users-table-wrapper">
            <table class="users-table">
                <thead>
                    <tr>
                        <th>User</th>
                        <th>Email</th>
                        <th>Habits</th>
                        <th>Goals</th>
                        <th>Achievements</th>
                        <th>Joined</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
            @endif

            <tr>
                <td>
                    <div class="user-cell">
                        <img class="user-avatar"
                             src="{{ $user->profile_picture ? asset('storage/' . $user->profile_picture) : asset('images/default_profile.png') }}"
                             alt="{{ $user->username }}">
                        <span>{{ $user->username }}</span>
                    </div>
                </td>
                <td>{{ $user->email }}</td>
                <td>{{ $user->habits_count }}</td>
                <td>{{ $user->goals_count }}</td>
                <td>{{ $user->achievements_count }}</td>
                <td>{{ $user->created_at->format('M d, Y') }}</td>
                <td>
                    @if($user->is_admin)
                        <span class="role-badge role-admin">Admin</span>
                    @else
                        <span class="role-badge role-user">User</span>
                    @endif
                </td>
                <td>
                    @if($user->id !== auth()->id())
                        <form action="{{ route('admin.users.destroy', $user) }}" method="POST"
                              onsubmit="return confirm('Delete user {{ $user->username }}? This cannot be undone.')">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn btn-delete">Delete</button>
                        </form>
                    @else
                        <span class="you-badge">You</span>
                    @endif
                </td>
            </tr>

        @if($loop->last)
            </tbody>
        </table>
        </div>
        @endif

    @empty
        <div id="placeholder-container">
            <img src="images/placeholder-img.png" alt="placeholder" id="placeholder-img">
            <p id="placeholder-msg">No users found</p>
        </div>
    @endforelse
</div>

@endsection