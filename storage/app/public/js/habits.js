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
        
        'Mindfullness': [
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
    
    // Check if elements exist
    if (!categorySelect || !iconGrid || !selectedIcon || !iconInput || !selectedIconDisplay) {
        console.error('Required elements not found!', {
            categorySelect: !!categorySelect,
            iconGrid: !!iconGrid,
            selectedIcon: !!selectedIcon,
            iconInput: !!iconInput,
            selectedIconDisplay: !!selectedIconDisplay
        });
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
    
    // Update icons when category changes
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
    
    // Popup functionality
    if (addHabitBtn && habitFormPopup && closePopupBtn) {
        // Function to show popup
        function showPopup() {
            habitFormPopup.style.display = 'block';
            // Reset form
            document.getElementById('habit-form').reset();
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
    
    console.log('Habit form initialized successfully');

    
});