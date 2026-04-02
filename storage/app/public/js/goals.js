document.addEventListener('DOMContentLoaded', function() {
    const categoryIcons = {
        'Nutrition': ['fa-solid fa-apple-whole', 'fa-solid fa-carrot', 'fa-solid fa-lemon', 'fa-solid fa-bowl-food', 'fa-solid fa-mug-saucer', 'fa-solid fa-burger', 'fa-solid fa-fish', 'fa-solid fa-egg', 'fa-solid fa-droplet', 'fa-solid fa-wine-bottle'],
        'Fitness': ['fa-solid fa-dumbbell', 'fa-solid fa-person-running', 'fa-solid fa-person-walking', 'fa-solid fa-bicycle', 'fa-solid fa-heart-pulse', 'fa-solid fa-fire', 'fa-solid fa-stopwatch', 'fa-solid fa-shoe-prints', 'fa-solid fa-weight-scale', 'fa-solid fa-person-swimming'],
        'Mindfulness': ['fa-solid fa-brain', 'fa-solid fa-heart', 'fa-solid fa-spa', 'fa-regular fa-face-smile', 'fa-solid fa-feather', 'fa-solid fa-leaf', 'fa-solid fa-om', 'fa-solid fa-cloud', 'fa-solid fa-wind', 'fa-regular fa-moon'],
        'Study': ['fa-solid fa-book', 'fa-solid fa-book-open', 'fa-solid fa-graduation-cap', 'fa-solid fa-pencil', 'fa-solid fa-pen', 'fa-solid fa-brain', 'fa-solid fa-lightbulb', 'fa-solid fa-microscope', 'fa-solid fa-flask', 'fa-solid fa-language'],
        'Work': ['fa-solid fa-briefcase', 'fa-solid fa-laptop', 'fa-solid fa-computer', 'fa-solid fa-clock', 'fa-solid fa-calendar-check', 'fa-solid fa-chart-line', 'fa-solid fa-chart-simple', 'fa-solid fa-envelope', 'fa-solid fa-users', 'fa-solid fa-mug-hot']
    };

    const elements = {
        category: document.getElementById('category'),
        iconGrid: document.getElementById('icon-grid'),
        selectedIcon: document.getElementById('selected-icon'),
        iconInput: document.getElementById('icon'),
        iconDisplay: document.getElementById('selected-icon-display'),
        addBtn: document.getElementById('addGoalButton'),
        popup: document.getElementById('goalFormPopup'),
        closeBtn: document.getElementById('closePopupBtn'),
        form: document.getElementById('goal-form'),
        filterBtns: document.querySelectorAll('.filter-btn'),
        stats: document.querySelectorAll('.stat-badge')
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

    const filterGoals = (filter) => {
        const goals = document.querySelectorAll('.goal-card');
        goals.forEach(goal => {
            if (filter === 'All') {
                goal.style.display = 'block';
            } else if (filter === 'Completed' && goal.classList.contains('completed')) {
                goal.style.display = 'block';
            } else if (filter === 'In Progress' && (goal.classList.contains('in-progress') || goal.classList.contains('not-started'))) {
                goal.style.display = 'block';
            } else {
                goal.style.display = 'none';
            }
        });
    };

    const updateStats = () => {
        const completed = document.querySelectorAll('.goal-card.completed').length;
        const inProgress = document.querySelectorAll('.goal-card.in-progress').length;
        const notStarted = document.querySelectorAll('.goal-card.not-started').length;
        
        if (elements.stats[0]) elements.stats[0].textContent = `${completed} Goals Completed`;
        if (elements.stats[1]) elements.stats[1].textContent = `${inProgress} Goals In Progress`;
        if (elements.stats[2]) elements.stats[2].textContent = `${notStarted} Goals Not Started`;
    };

    const showPopup = () => {
        elements.popup.style.display = 'block';
        if (elements.form) elements.form.reset();
        elements.iconDisplay.className = 'fa-solid fa-bullseye';
        elements.iconInput.value = 'fa-solid fa-bullseye';
        if (elements.category) elements.category.value = '';
        elements.iconGrid.innerHTML = '';
        elements.iconGrid.style.display = 'none';
    };

    const hidePopup = () => elements.popup.style.display = 'none';

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
                elements.iconDisplay.className = 'fa-solid fa-bullseye';
                elements.iconInput.value = 'fa-solid fa-bullseye';
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

    if (elements.filterBtns) {
        elements.filterBtns.forEach(btn => {
            btn.onclick = () => {
                elements.filterBtns.forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
                filterGoals(btn.textContent.trim());
            };
        });
    }

    if (elements.form) {
        elements.form.onsubmit = (e) => {
            e.preventDefault();
            const formData = new FormData(elements.form);
            
            fetch(elements.form.action, {
                method: 'POST',
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
                },
                body: formData
            }).then(response => response.json())
              .then(data => {
                  if (data.success || data.id) {
                      hidePopup();
                      alert('Goal added successfully!');
                      location.reload();
                  } else if (data.errors) {
                      let errorMsg = 'Please fix the following errors:\n';
                      Object.values(data.errors).forEach(error => {
                          errorMsg += `- ${error[0]}\n`;
                      });
                      alert(errorMsg);
                  }
              }).catch(() => alert('Error adding goal'));
        };
    }

    document.querySelectorAll('.goal-card form').forEach(form => {
        form.onsubmit = (e) => {
            e.preventDefault();
            const formData = new FormData(form);
            
            fetch(form.action, {
                method: 'POST',
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
                },
                body: formData
            }).then(response => response.json())
              .then(data => {
                  if (data.success) {
                      location.reload();
                  }
              }).catch(() => alert('Error updating progress'));
        };
    });

    if (elements.iconInput && elements.iconInput.value && elements.iconInput.value !== 'fa-solid fa-bullseye') {
        elements.iconDisplay.className = elements.iconInput.value;
    }

    updateStats();
});