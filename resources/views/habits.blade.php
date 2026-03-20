@extends('layouts.dashboard_app')

@section('dashboard-styles')
    <link rel="stylesheet" href="{{ asset('storage/css/dashboard_habits.css') }}">
@endsection

@section('title', 'Habits')

@section('content')
    <div class="dashboard-header">
        <div class="welcome-section">
            <h1>Manage habits</h1>
        </div>
    </div>

    <!-- Habit Form -->
    <div class="input-section">
        <input type="text" id="habit-name" placeholder="Enter habit name..." class="habit-input">
        <select id="habit-category" class="habit-input">
            <option value="Nutrition">Nutrition</option>
            <option value="Fitness">Fitness</option>
            <option value="Mindfulness">Mindfulness</option>
            <option value="Study">Study</option>
            <option value="Work">Work</option>
        </select>
        <select id="habit-frequency" class="habit-input">
            <option value="daily">Daily</option>
            <option value="weekly">Weekly</option>
            <option value="monthly">Monthly</option>
        </select>
        <button class="btn btn-add" onclick="addHabit()">Add habit</button>
    </div>

    <!-- Habits List -->
    <div class="habits-section">
        <div class="habits-header">
            <span></span>
            <span>Name</span>
            <span>Frequency</span>
            <span>Status</span>
            <span>Actions</span>
        </div>
        <div id="habits-list">
            <p id="loading-msg">Loading habits...</p>
        </div>
    </div>

    <script>
        // Load habits
        document.addEventListener('DOMContentLoaded', loadHabits);

        function loadHabits() {
            apiGet('/api/habits').then(habits => {
                const list = document.getElementById('habits-list');

                if (habits.length === 0) {
                    list.innerHTML = '<p>No habits yet. Add your first one above!</p>';
                    return;
                }

                list.innerHTML = habits.map(habit => `
                    <div class="habit-item" id="habit-${habit.id}">
                        <span class="habit-icon"><i class="fa-solid fa-star"></i></span>
                        <span>${habit.name}</span>
                        <span>${habit.frequency}</span>
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch"
                                onchange="toggleComplete(${habit.id}, this)">
                        </div>
                        <span>
                            <button class="icon-btn" onclick="deleteHabit(${habit.id})">
                                <i class="fa-solid fa-trash"></i>
                            </button>
                        </span>
                    </div>
                `).join('');
            });
        }

        function addHabit() {
            const name      = document.getElementById('habit-name').value.trim();
            const category  = document.getElementById('habit-category').value;
            const frequency = document.getElementById('habit-frequency').value;

            if (!name) {
                alert('Please enter a habit name.');
                return;
            }

            apiPost('/api/habits', { name, category, frequency })
                .then(habit => {
                    document.getElementById('habit-name').value = '';
                    loadHabits();
                });
        }

        function deleteHabit(id) {
            if (!confirm('Delete this habit?')) return;

            apiDelete('/api/habits/' + id).then(() => {
                document.getElementById('habit-' + id).remove();
            });
        }

        function toggleComplete(habitId, checkbox) {
            apiPost('/api/habit-completions', { habit_id: habitId })
                .then(data => {
                    checkbox.disabled = true;
                })
                .catch(() => {
                    checkbox.checked = false;
                });
        }
    </script>
@endsection
