@extends('layouts.dashboard_app')

@section('title', 'Habits')

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
                        <a href="/habits">
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
@endsection