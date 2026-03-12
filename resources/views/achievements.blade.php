@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_achievements.css') }}">
@endsection

@section('title', 'Achievements')

@section('content')
    <!-- Title -->
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
@endsection