document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM loaded - initializing habit form');
    
    const categoryIcons = {
        'Nutrition': [
            'fa-solid fa-apple-whole',
            'fa-solid fa-carrot',
            'fa-solid fa-lemon',
            'fa-solid fa-bowl-food',
            'fa-solid fa-mug-saucer',
            'fa-solid fa-burger',
            'fa-solid fa-fish',
            'fa-solid fa-egg',
            'fa-solid fa-droplet',
            'fa-solid fa-wine-bottle',
        ],
        
        'Fitness': [
            'fa-solid fa-dumbbell',
            'fa-solid fa-person-running',
            'fa-solid fa-person-walking',
            'fa-solid fa-bicycle',
            'fa-solid fa-heart-pulse',
            'fa-solid fa-fire',
            'fa-solid fa-stopwatch',
            'fa-solid fa-shoe-prints',
            'fa-solid fa-weight-scale',
            'fa-solid fa-person-swimming',
        ],
        
        'Mindfulness': [
            'fa-solid fa-brain',
            'fa-solid fa-heart',
            'fa-solid fa-spa',
            'fa-regular fa-face-smile',
            'fa-solid fa-feather',
            'fa-solid fa-leaf',
            'fa-solid fa-om',
            'fa-solid fa-cloud',
            'fa-solid fa-wind',
            'fa-regular fa-moon',
        ],
        
        'Study': [
            'fa-solid fa-book',
            'fa-solid fa-book-open',
            'fa-solid fa-graduation-cap',
            'fa-solid fa-pencil',
            'fa-solid fa-pen',
            'fa-solid fa-brain',
            'fa-solid fa-lightbulb',
            'fa-solid fa-microscope',
            'fa-solid fa-flask',
            'fa-solid fa-language',
        ],
        
        'Work': [
            'fa-solid fa-briefcase',
            'fa-solid fa-laptop',
            'fa-solid fa-computer',
            'fa-solid fa-clock',
            'fa-solid fa-calendar-check',
            'fa-solid fa-chart-line',
            'fa-solid fa-chart-simple',
            'fa-solid fa-envelope',
            'fa-solid fa-users',
            'fa-solid fa-mug-hot',
        ]
    };

    const categorySelect = document.getElementById('category');
    const iconGrid = document.getElementById('icon-grid');
    const selectedIcon = document.getElementById('selected-icon');
    const iconInput = document.getElementById('icon');
    const selectedIconDisplay = document.getElementById('selected-icon-display');
    const addHabitBtn = document.getElementById('addHabitBtn');
    const habitFormPopup = document.getElementById('habitFormPopup');
    const closePopupBtn = document.getElementById('closePopupBtn');
    const habitForm = document.getElementById('habit-form');
    
    function apiGet(url) {
        return fetch(url, {
            headers: {
                'Accept': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
            }
        }).then(response => {
            if (!response.ok) throw new Error('HTTP ' + response.status);
            return response.json();
        });
    }
    
    function apiPost(url, data) {
        return fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
            },
            body: JSON.stringify(data)
        }).then(response => response.json());
    }
    
    function apiDelete(url) {
        return fetch(url, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
            }
        }).then(response => {
            if (response.status === 204) return null;
            return response.json();
        });
    }
    
    if (!categorySelect || !iconGrid || !selectedIcon || !iconInput || !selectedIconDisplay) {
        console.error('Required elements not found!');
        return;
    }
    
    console.log('All elements found, initializing...');
    
    function populateIcons(icons) {
        console.log('Populating icons:', icons);
        iconGrid.innerHTML = '';
        
        icons.forEach(iconClass => {
            const iconWrapper = document.createElement('div');
            iconWrapper.style.cursor = 'pointer';
            iconWrapper.style.padding = '10px';
            iconWrapper.style.borderRadius = '8px';
            iconWrapper.style.display = 'flex';
            iconWrapper.style.alignItems = 'center';
            iconWrapper.style.justifyContent = 'center';
            iconWrapper.style.transition = 'all 0.2s';
            
            const iconElement = document.createElement('i');
            iconElement.className = iconClass;
            iconElement.style.fontSize = '24px';
            
            iconWrapper.appendChild(iconElement);
            
            iconWrapper.addEventListener('mouseenter', function() {
                this.style.backgroundColor = '#f0f0f0';
            });
            
            iconWrapper.addEventListener('mouseleave', function() {
                this.style.backgroundColor = 'transparent';
            });
            
            iconWrapper.addEventListener('click', function() {
                selectIcon(iconClass);
            });
            
            iconGrid.appendChild(iconWrapper);
        });
        
        iconGrid.style.display = 'grid';
    }
    
    function selectIcon(iconClass) {
        console.log('Selected icon:', iconClass);
        selectedIconDisplay.className = iconClass;
        selectedIconDisplay.style.fontSize = '24px';
        iconInput.value = iconClass;
        iconGrid.style.display = 'none';

        selectedIcon.style.borderColor = '#4CAF50';
        setTimeout(() => {
            selectedIcon.style.borderColor = '#ddd';
        }, 500);
    }
    
    if (selectedIcon) {
        selectedIcon.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            console.log('Selected icon clicked');
            
            if (!categorySelect.value) {
                alert('Please select a category first');
                return;
            }
            
            if (!categoryIcons[categorySelect.value]) {
                alert('Please select a valid category');
                return;
            }
            
            const isVisible = iconGrid.style.display === 'grid';
            iconGrid.style.display = isVisible ? 'none' : 'grid';
            console.log('Icon grid visibility toggled:', !isVisible);
        });
    }
    
    if (categorySelect) {
        categorySelect.addEventListener('change', function() {
            const category = this.value;
            console.log('Category changed to:', category);
            
            if (category && categoryIcons[category]) {
                populateIcons(categoryIcons[category]);

                selectedIconDisplay.className = 'fa-solid fa-smile';
                selectedIconDisplay.style.fontSize = '24px';
                iconInput.value = 'fa-solid fa-smile';
            } else {
                iconGrid.innerHTML = '';
                iconGrid.style.display = 'none';
            }
        });
    }
    
    document.addEventListener('click', function(e) {
        const iconSelector = document.getElementById('icon-selector');
        if (iconSelector && !iconSelector.contains(e.target)) {
            iconGrid.style.display = 'none';
        }
    });
    
    if (categorySelect.value && categoryIcons[categorySelect.value]) {
        console.log('Pre-selected category found:', categorySelect.value);
        populateIcons(categoryIcons[categorySelect.value]);
    }
    
    if (iconInput.value && iconInput.value !== 'fa-solid fa-smile') {
        selectedIconDisplay.className = iconInput.value;
        selectedIconDisplay.style.fontSize = '24px';
    }
    
    function renderHabits(habits) {
        const list = document.getElementById('habits-list');
        if (!list) return;
        
        if (!habits || !Array.isArray(habits) || habits.length === 0) {
            list.innerHTML = `
                <div id="placeholder-container">
                    <img src="images/placeholder-img.png" alt="placeholder" id="placeholder-img">
                    <p id="placeholder-msg">No habits added</p>
                </div>`;
            return;
        }
        
        list.innerHTML = habits.map(h => `
            <div class="habit-item" data-id="${h.id}">
                <div class="habit-icon">
                    <i class="${h.icon || 'fa-solid fa-smile'}"></i>
                </div>
                <div class="habit-name">${escapeHtml(h.name)}</div>
                <div class="habit-frequency">${h.frequency}</div>
                <div class="habit-status">
                    <span class="status-${h.is_active ? 'active' : 'paused'}">${h.is_active ? 'Active' : 'Paused'}</span>
                </div>
                <div class="habit-actions">
                    <button class="edit-btn" data-id="${h.id}"><i class="fa-solid fa-edit"></i></button>
                    <button class="delete-btn" data-id="${h.id}"><i class="fa-solid fa-trash"></i></button>
                </div>
            </div>`).join('');
        
        list.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', () => deleteHabit(btn.dataset.id));
        });
        
        list.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', () => editHabit(btn.dataset.id));
        });
    }
    
    function escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }
    
    function loadHabits() {
        apiGet('/api/habits').then(renderHabits).catch(error => {
            console.error('Error loading habits:', error);
            const list = document.getElementById('habits-list');
            if (list) {
                list.innerHTML = `
                    <div id="placeholder-container">
                        <p id="placeholder-msg">Could not load habits. Please refresh the page.</p>
                    </div>`;
            }
        });
    }
    
    function deleteHabit(id) {
        if (confirm('Are you sure you want to delete this habit?')) {
            apiDelete('/api/habits/' + id).then(() => {
                loadHabits();
            }).catch(error => {
                console.error('Error deleting habit:', error);
                alert('Error deleting habit');
            });
        }
    }
    
    function editHabit(id) {
        console.log('Edit habit:', id);
    }
    
    if (addHabitBtn && habitFormPopup && closePopupBtn) {
        function showPopup() {
            habitFormPopup.style.display = 'block';
            if (habitForm) habitForm.reset();
            selectedIconDisplay.className = 'fa-solid fa-smile';
            selectedIconDisplay.style.fontSize = '24px';
            iconInput.value = 'fa-solid fa-smile';
            categorySelect.value = '';
            iconGrid.innerHTML = '';
            iconGrid.style.display = 'none';
        }
        
        function hidePopup() {
            habitFormPopup.style.display = 'none';
        }
        
        addHabitBtn.addEventListener('click', function(e) {
            e.preventDefault();
            showPopup();
        });
        
        closePopupBtn.addEventListener('click', function(e) {
            e.preventDefault();
            hidePopup();
        });
        
        habitFormPopup.addEventListener('click', function(e) {
            if (e.target === habitFormPopup) {
                hidePopup();
            }
        });
        
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape' && habitFormPopup.style.display === 'block') {
                hidePopup();
            }
        });
    } else {
        console.error('Popup elements not found!', {
            addHabitBtn: !!addHabitBtn,
            habitFormPopup: !!habitFormPopup,
            closePopupBtn: !!closePopupBtn
        });
    }
    
    if (habitForm) {
        habitForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const data = {
                name: document.getElementById('name').value,
                description: document.getElementById('description').value,
                category: document.getElementById('category').value,
                frequency: document.getElementById('frequency').value,
                target_count: document.getElementById('target_count').value,
                icon: document.getElementById('icon').value,
            };
            
            apiPost('/api/habits', data).then(habit => {
                if (habit.id || habit.success) {
                    hidePopup();
                    loadHabits();
                    alert('Habit added successfully!');
                    habitForm.reset();
                } else if (habit.errors) {
                    alert('Please check the form for errors');
                }
            }).catch(error => {
                console.error('Error adding habit:', error);
                alert('Error adding habit');
            });
        });
    }
    
    loadHabits();
    
    console.log('Habit form initialized successfully');
});