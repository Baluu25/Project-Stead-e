// Wait for DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM loaded - initializing habit form');
    
    // Icons data
    const categoryIcons = {
        'Nutrition': [
            'fa-solid fa-apple',
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

    // DOM elements
    const categorySelect = document.getElementById('category');
    const iconGrid = document.getElementById('icon-grid');
    const selectedIcon = document.getElementById('selected-icon');
    const iconInput = document.getElementById('icon');
    const selectedIconDisplay = document.getElementById('selected-icon-display');
    const addHabitBtn = document.getElementById('addHabitBtn');
    const habitFormPopup = document.getElementById('habitFormPopup');
    const closePopupBtn = document.getElementById('closePopupBtn');
    const habitForm = document.getElementById('habit-form');
    
    // API helper functions
    function apiGet(url) {
        return fetch(url, {
            headers: {
                'Accept': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
            }
        }).then(response => response.json());
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
    
    // Check if elements exist
    if (!categorySelect || !iconGrid || !selectedIcon || !iconInput || !selectedIconDisplay) {
        console.error('Required elements not found!');
        return;
    }
    
    console.log('All elements found, initializing...');
    
    // Function to populate icons
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
    
    // Function to select an icon
    function selectIcon(iconClass) {
        console.log('Selected icon:', iconClass);
        selectedIconDisplay.className = iconClass;
        selectedIconDisplay.style.fontSize = '24px';
        iconInput.value = iconClass;
        iconGrid.style.display = 'none';
        
        // Add visual feedback
        selectedIcon.style.borderColor = '#4CAF50';
        setTimeout(() => {
            selectedIcon.style.borderColor = '#ddd';
        }, 500);
    }
    
    // Toggle icon grid visibility
    if (selectedIcon) {
        selectedIcon.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            console.log('Selected icon clicked');
            
            // Check if a category is selected
            if (!categorySelect.value) {
                alert('Please select a category first');
                return;
            }
            
            // Check if icons are populated for this category
            if (!categoryIcons[categorySelect.value]) {
                alert('Please select a valid category');
                return;
            }
            
            const isVisible = iconGrid.style.display === 'grid';
            iconGrid.style.display = isVisible ? 'none' : 'grid';
            console.log('Icon grid visibility toggled:', !isVisible);
        });
    }
    
    // Update icons when category changes
    if (categorySelect) {
        categorySelect.addEventListener('change', function() {
            const category = this.value;
            console.log('Category changed to:', category);
            
            if (category && categoryIcons[category]) {
                populateIcons(categoryIcons[category]);
                // Reset selected icon
                selectedIconDisplay.className = 'fa-solid fa-smile';
                selectedIconDisplay.style.fontSize = '24px';
                iconInput.value = 'fa-solid fa-smile';
            } else {
                iconGrid.innerHTML = '';
                iconGrid.style.display = 'none';
            }
        });
    }
    
    // Close icon grid when clicking outside
    document.addEventListener('click', function(e) {
        const iconSelector = document.getElementById('icon-selector');
        if (iconSelector && !iconSelector.contains(e.target)) {
            iconGrid.style.display = 'none';
        }
    });
    
    // Initialize if category is pre-selected
    if (categorySelect.value && categoryIcons[categorySelect.value]) {
        console.log('Pre-selected category found:', categorySelect.value);
        populateIcons(categoryIcons[categorySelect.value]);
    }
    
    // Initialize icon display if there's an old value
    if (iconInput.value && iconInput.value !== 'fa-solid fa-smile') {
        selectedIconDisplay.className = iconInput.value;
        selectedIconDisplay.style.fontSize = '24px';
    }
    
    // Render habits function
    function renderHabits(habits) {
        const list = document.getElementById('habits-list');
        if (!list) return;
        
        if (!habits || habits.length === 0) {
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
                    <button class="edit-btn" data-id="${h.id}"></button>
                    <button class="delete-btn" data-id="${h.id}"></button>
                </div>
            </div>`).join('');
        
        // Add delete event listeners
        list.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', () => deleteHabit(btn.dataset.id));
        });
        
        // Add edit event listeners
        list.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', () => editHabit(btn.dataset.id));
        });
    }
    
    // Helper function to escape HTML
    function escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }
    
    // Load habits function
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
    
    // Delete habit function
    function deleteHabit(id) {
        if (confirm('Are you sure you want to delete this habit?')) {
            apiDelete('/api/habits/' + id).then(() => {
                loadHabits();
                showNotification('Habit deleted successfully!', 'success');
            }).catch(error => {
                console.error('Error deleting habit:', error);
                showNotification('Error deleting habit', 'error');
            });
        }
    }
    
    // Edit habit function
    function editHabit(id) {
        console.log('Edit habit:', id);
        // Implement edit functionality
    }
    
    // Show notification function
    function showNotification(message, type = 'success') {
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        notification.textContent = message;
        notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 12px 20px;
            background: ${type === 'success' ? '#4CAF50' : '#f44336'};
            color: white;
            border-radius: 8px;
            z-index: 10000;
            animation: slideIn 0.3s ease;
        `;
        
        document.body.appendChild(notification);
        
        setTimeout(() => {
            notification.style.animation = 'slideOut 0.3s ease';
            setTimeout(() => notification.remove(), 300);
        }, 3000);
    }
    
    // Popup functionality
    if (addHabitBtn && habitFormPopup && closePopupBtn) {
        // Function to show popup
        function showPopup() {
            habitFormPopup.style.display = 'block';
            if (habitForm) habitForm.reset();
            // Reset icon
            selectedIconDisplay.className = 'fa-solid fa-smile';
            selectedIconDisplay.style.fontSize = '24px';
            iconInput.value = 'fa-solid fa-smile';
            // Reset category
            categorySelect.value = '';
            iconGrid.innerHTML = '';
            iconGrid.style.display = 'none';
        }
        
        // Function to hide popup
        function hidePopup() {
            habitFormPopup.style.display = 'none';
        }
        
        // Show popup when Add habit button is clicked
        addHabitBtn.addEventListener('click', function(e) {
            e.preventDefault();
            showPopup();
        });
        
        // Hide popup when close button is clicked
        closePopupBtn.addEventListener('click', function(e) {
            e.preventDefault();
            hidePopup();
        });
        
        // Hide popup when clicking outside
        habitFormPopup.addEventListener('click', function(e) {
            if (e.target === habitFormPopup) {
                hidePopup();
            }
        });
        
        // Hide popup when Escape key is pressed
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
    
    // Handle form submission
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
                    showNotification('Habit added successfully!', 'success');
                    habitForm.reset();
                } else if (habit.errors) {
                    // Handle validation errors
                    showNotification('Please check the form for errors', 'error');
                }
            }).catch(error => {
                console.error('Error adding habit:', error);
                showNotification('Error adding habit', 'error');
            });
        });
    }
    
    // Load habits on page load
    loadHabits();
    
    console.log('Habit form initialized successfully');
});

// Add CSS animations for notifications
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(100%);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);