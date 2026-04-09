document.addEventListener('DOMContentLoaded', function() {
    // Category icons data
    const categoryIcons = {
        'Nutrition': ['fa-solid fa-apple-whole', 'fa-solid fa-carrot', 'fa-solid fa-lemon', 'fa-solid fa-bowl-food', 'fa-solid fa-mug-saucer', 'fa-solid fa-burger', 'fa-solid fa-fish', 'fa-solid fa-egg', 'fa-solid fa-droplet', 'fa-solid fa-wine-bottle'],
        'Fitness': ['fa-solid fa-dumbbell', 'fa-solid fa-person-running', 'fa-solid fa-person-walking', 'fa-solid fa-bicycle', 'fa-solid fa-heart-pulse', 'fa-solid fa-fire', 'fa-solid fa-stopwatch', 'fa-solid fa-shoe-prints', 'fa-solid fa-weight-scale', 'fa-solid fa-person-swimming'],
        'Mindfulness': ['fa-solid fa-brain', 'fa-solid fa-heart', 'fa-solid fa-spa', 'fa-regular fa-face-smile', 'fa-solid fa-feather', 'fa-solid fa-leaf', 'fa-solid fa-om', 'fa-solid fa-cloud', 'fa-solid fa-wind', 'fa-regular fa-moon'],
        'Study': ['fa-solid fa-book', 'fa-solid fa-book-open', 'fa-solid fa-graduation-cap', 'fa-solid fa-pencil', 'fa-solid fa-pen', 'fa-solid fa-brain', 'fa-solid fa-lightbulb', 'fa-solid fa-microscope', 'fa-solid fa-flask', 'fa-solid fa-language'],
        'Work': ['fa-solid fa-briefcase', 'fa-solid fa-laptop', 'fa-solid fa-computer', 'fa-solid fa-clock', 'fa-solid fa-calendar-check', 'fa-solid fa-chart-line', 'fa-solid fa-chart-simple', 'fa-solid fa-envelope', 'fa-solid fa-users', 'fa-solid fa-mug-hot']
    };

    // DOM elements
    const elements = {
        category: document.getElementById('category'),
        iconGrid: document.getElementById('icon-grid'),
        selectedIcon: document.getElementById('selected-icon'),
        iconInput: document.getElementById('icon'),
        iconDisplay: document.getElementById('selected-icon-display'),
        addBtn: document.getElementById('addHabitBtn'),
        popup: document.getElementById('habitFormPopup'),
        closeBtn: document.getElementById('closePopupBtn'),
        form: document.getElementById('habit-form'),
        habitsList: document.getElementById('habits-list')
    };

    // Helper functions
    const api = {
        get: (url) => fetch(url, {
            headers: {
                'Accept': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
            }
        }).then(res => res.ok ? res.json() : Promise.reject('HTTP ' + res.status)),
        
        post: (url, data) => fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
            },
            body: JSON.stringify(data)
        }).then(res => res.json()),
        
        delete: (url) => fetch(url, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
            }
        }).then(res => res.status === 204 ? null : res.json())
    };

    const escapeHtml = (text) => {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    };

    const selectIcon = (iconClass) => {
        elements.iconDisplay.className = iconClass;
        elements.iconInput.value = iconClass;
        elements.iconGrid.style.display = 'none';
        elements.selectedIcon.style.borderColor = '#4CAF50';
        setTimeout(() => elements.selectedIcon.style.borderColor = '#ddd', 500);
    };

    const populateIcons = (icons) => {
        elements.iconGrid.innerHTML = '';
        icons.forEach(iconClass => {
            const wrapper = document.createElement('div');
            wrapper.style.cssText = 'cursor:pointer;padding:10px;border-radius:8px;display:flex;align-items:center;justify-content:center;transition:all 0.2s';
            const icon = document.createElement('i');
            icon.className = iconClass;
            icon.style.fontSize = '24px';
            wrapper.appendChild(icon);
            wrapper.onmouseenter = () => wrapper.style.backgroundColor = '#f0f0f0';
            wrapper.onmouseleave = () => wrapper.style.backgroundColor = 'transparent';
            wrapper.onclick = () => selectIcon(iconClass);
            elements.iconGrid.appendChild(wrapper);
        });
        elements.iconGrid.style.display = 'grid';
    };

    const loadHabits = () => {
        api.get('/api/habits').then(habits => {
            if (!habits || habits.length === 0) {
                elements.habitsList.innerHTML = `<div id="placeholder-container"><img src="images/placeholder-img.png" alt="placeholder" id="placeholder-img"><p id="placeholder-msg">No habits added</p></div>`;
                return;
            }
            elements.habitsList.innerHTML = habits.map(h => {
                const isCompleted = (h.completed_today ?? 0) >= (h.target_count ?? 1);
                const statusClass = isCompleted ? 'completed' : (h.is_active ? 'active' : 'paused');
                const statusText  = isCompleted ? 'Completed' : (h.is_active ? 'Active' : 'Paused');
    
                return `
                    <div class="habit-item" data-id="${h.id}">
                        <div class="habit-icon"><i class="${h.icon || 'fa-solid fa-smile'}"></i></div>
                        <div class="habit-name">${escapeHtml(h.name)}</div>
                        <div class="habit-frequency">${h.frequency}</div>
                        <div class="habit-status"><span class="status-${statusClass}">${statusText}</span></div>
                        <div class="habit-actions">
                            <button class="edit-btn" data-id="${h.id}"><i class="fa-solid fa-edit"></i></button>
                            <button class="delete-btn" data-id="${h.id}"><i class="fa-solid fa-trash"></i></button>
                        </div>
                    </div>
                    `;
        }).join('');
            
            document.querySelectorAll('.delete-btn').forEach(btn => btn.onclick = () => deleteHabit(btn.dataset.id));
            document.querySelectorAll('.edit-btn').forEach(btn => btn.onclick = () => console.log('Edit habit:', btn.dataset.id));
        }).catch(() => {
            if (elements.habitsList) elements.habitsList.innerHTML = `<div id="placeholder-container"><p id="placeholder-msg">Could not load habits. Please refresh the page.</p></div>`;
        });
    };

    const deleteHabit = (id) => {
        if (confirm('Are you sure you want to delete this habit?')) {
            api.delete('/api/habits/' + id).then(() => loadHabits()).catch(() => alert('Error deleting habit'));
        }
    };

    const showPopup = () => {
        elements.popup.style.display = 'block';
        if (elements.form) elements.form.reset();
        elements.iconDisplay.className = 'fa-solid fa-smile';
        elements.iconInput.value = 'fa-solid fa-smile';
        elements.category.value = '';
        elements.iconGrid.innerHTML = '';
        elements.iconGrid.style.display = 'none';
    };

    const hidePopup = () => elements.popup.style.display = 'none';

    // Event listeners
    if (elements.selectedIcon) {
        elements.selectedIcon.onclick = (e) => {
            e.preventDefault();
            if (!elements.category.value) return alert('Please select a category first');
            if (!categoryIcons[elements.category.value]) return alert('Please select a valid category');
            elements.iconGrid.style.display = elements.iconGrid.style.display === 'grid' ? 'none' : 'grid';
        };
    }

    if (elements.category) {
        elements.category.onchange = () => {
            const category = elements.category.value;
            if (category && categoryIcons[category]) {
                populateIcons(categoryIcons[category]);
                elements.iconDisplay.className = 'fa-solid fa-smile';
                elements.iconInput.value = 'fa-solid fa-smile';
            } else {
                elements.iconGrid.innerHTML = '';
                elements.iconGrid.style.display = 'none';
            }
        };
    }

    document.onclick = (e) => {
        const selector = document.getElementById('icon-selector');
        if (selector && !selector.contains(e.target)) elements.iconGrid.style.display = 'none';
    };

    if (elements.addBtn && elements.popup && elements.closeBtn) {
        elements.addBtn.onclick = (e) => { e.preventDefault(); showPopup(); };
        elements.closeBtn.onclick = (e) => { e.preventDefault(); hidePopup(); };
        elements.popup.onclick = (e) => { if (e.target === elements.popup) hidePopup(); };
        document.onkeydown = (e) => { if (e.key === 'Escape' && elements.popup.style.display === 'block') hidePopup(); };
    }

    if (elements.form) {
        elements.form.onsubmit = (e) => {
            e.preventDefault();
            const data = {
                name: document.getElementById('name').value,
                description: document.getElementById('description').value,
                category: document.getElementById('category').value,
                frequency: document.getElementById('frequency').value,
                target_count: document.getElementById('target_count').value,
                icon: document.getElementById('icon').value,
            };
            api.post('/api/habits', data).then(habit => {
                if (habit.id || habit.success) {
                    hidePopup();
                    loadHabits();
                    alert('Habit added successfully!');
                    elements.form.reset();
                } else if (habit.errors) alert('Please check the form for errors');
            }).catch(() => alert('Error adding habit'));
        };
    }

    if (elements.iconInput.value && elements.iconInput.value !== 'fa-solid fa-smile') {
        elements.iconDisplay.className = elements.iconInput.value;
    }

    loadHabits();
});