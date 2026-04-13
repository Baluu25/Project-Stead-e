document.addEventListener('DOMContentLoaded', function () {

    const categoryIcons = {
        'general':  ['fa-solid fa-apple-whole', 'fa-solid fa-carrot', 'fa-solid fa-lemon', 'fa-solid fa-bowl-food', 'fa-solid fa-mug-saucer', 'fa-solid fa-burger', 'fa-solid fa-fish', 'fa-solid fa-egg', 'fa-solid fa-droplet', 'fa-solid fa-wine-bottle'],
        'fitness':  ['fa-solid fa-dumbbell', 'fa-solid fa-person-running', 'fa-solid fa-person-walking', 'fa-solid fa-bicycle', 'fa-solid fa-heart-pulse', 'fa-solid fa-fire', 'fa-solid fa-stopwatch', 'fa-solid fa-shoe-prints', 'fa-solid fa-weight-scale', 'fa-solid fa-person-swimming'],
        'health':   ['fa-solid fa-brain', 'fa-solid fa-heart', 'fa-solid fa-spa', 'fa-regular fa-face-smile', 'fa-solid fa-feather', 'fa-solid fa-leaf', 'fa-solid fa-om', 'fa-solid fa-cloud', 'fa-solid fa-wind', 'fa-regular fa-moon'],
        'career':   ['fa-solid fa-book', 'fa-solid fa-book-open', 'fa-solid fa-graduation-cap', 'fa-solid fa-pencil', 'fa-solid fa-pen', 'fa-solid fa-brain', 'fa-solid fa-lightbulb', 'fa-solid fa-microscope', 'fa-solid fa-flask', 'fa-solid fa-language'],
        'learning': ['fa-solid fa-briefcase', 'fa-solid fa-laptop', 'fa-solid fa-computer', 'fa-solid fa-clock', 'fa-solid fa-calendar-check', 'fa-solid fa-chart-line', 'fa-solid fa-chart-simple', 'fa-solid fa-envelope', 'fa-solid fa-users', 'fa-solid fa-mug-hot']
    };

    const category      = document.getElementById('category');
    const iconGrid      = document.getElementById('icon-grid');
    const selectedIcon  = document.getElementById('selected-icon');
    const iconInput     = document.getElementById('icon');
    const iconDisplay   = document.getElementById('selected-icon-display');
    const addBtn        = document.getElementById('addGoalButton');
    const popup         = document.getElementById('goalFormPopup');
    const closeBtn      = document.getElementById('closePopupBtn');
    const form          = document.getElementById('goal-form');
    const filterBtns    = document.querySelectorAll('.filter-btn');
    const csrf          = document.querySelector('meta[name="csrf-token"]');

    window.updateStats = function() {
        const statBadges = document.querySelectorAll('.stat-badge');
        const completed  = document.querySelectorAll('.goal-card.completed').length;
        const inProgress = document.querySelectorAll('.goal-card.in-progress').length;
        const notStarted = document.querySelectorAll('.goal-card.not-started').length;
        
        if (statBadges[0]) statBadges[0].textContent = completed  + ' Goals Completed';
        if (statBadges[1]) statBadges[1].textContent = inProgress + ' Goals In Progress';
        if (statBadges[2]) statBadges[2].textContent = notStarted + ' Goals Not Started';
    };

    function selectIcon(iconClass) {
        iconDisplay.className = iconClass;
        iconInput.value = iconClass;
        iconGrid.style.display = 'none';
        selectedIcon.style.borderColor = '#4CAF50';
        setTimeout(() => selectedIcon.style.borderColor = '#ddd', 500);
    }

    function populateIcons(icons) {
        iconGrid.innerHTML = '';
        icons.forEach(iconClass => {
            const wrapper = document.createElement('div');
            wrapper.style.cssText = 'cursor:pointer;padding:10px;border-radius:8px;display:flex;align-items:center;justify-content:center;transition:background 0.2s';
            const icon = document.createElement('i');
            icon.className = iconClass;
            icon.style.fontSize = '24px';
            wrapper.appendChild(icon);
            wrapper.onmouseenter = () => wrapper.style.backgroundColor = '#f0f0f0';
            wrapper.onmouseleave = () => wrapper.style.backgroundColor = 'transparent';
            wrapper.onclick = () => selectIcon(iconClass);
            iconGrid.appendChild(wrapper);
        });
        iconGrid.style.display = 'grid';
    }

    if (selectedIcon) {
        selectedIcon.onclick = e => {
            e.preventDefault();
            if (!category.value) return alert('Please select a category first.');
            if (!categoryIcons[category.value]) return alert('No icons for this category.');
            iconGrid.style.display = iconGrid.style.display === 'grid' ? 'none' : 'grid';
        };
    }

    if (category) {
        category.onchange = () => {
            const val = category.value;
            if (val && categoryIcons[val]) {
                populateIcons(categoryIcons[val]);
                iconDisplay.className = 'fa-solid fa-bullseye';
                iconInput.value = 'fa-solid fa-bullseye';
            } else {
                iconGrid.innerHTML = '';
                iconGrid.style.display = 'none';
            }
        };
    }

    document.onclick = e => {
        const selector = document.getElementById('icon-selector');
        if (selector && !selector.contains(e.target)) iconGrid.style.display = 'none';
    };

    function showPopup() {
        popup.style.display = 'block';
        if (form) form.reset();
        iconDisplay.className = 'fa-solid fa-bullseye';
        iconInput.value = 'fa-solid fa-bullseye';
        if (category) category.value = '';
        iconGrid.innerHTML = '';
        iconGrid.style.display = 'none';
    }

    function hidePopup() {
        popup.style.display = 'none';
    }

    if (addBtn) addBtn.onclick = e => { e.preventDefault(); showPopup(); };
    if (closeBtn) closeBtn.onclick = e => { e.preventDefault(); hidePopup(); };
    if (popup) popup.onclick = e => { if (e.target === popup) hidePopup(); };

    document.onkeydown = e => {
        if (e.key === 'Escape' && popup && popup.style.display === 'block') hidePopup();
    };

    if (form) {
        form.onsubmit = e => {
            e.preventDefault();
            fetch(form.action, {
                method: 'POST',
                headers: { 'X-CSRF-TOKEN': csrf ? csrf.content : '' },
                body: new FormData(form)
            })
            .then(response => response.ok ? (hidePopup(), location.reload()) : alert('Could not save goal.'))
            .catch(() => alert('Network error.'));
        };
    }

    document.querySelectorAll('.goal-progress-actions').forEach(actions => {
        const removeBtn    = actions.querySelector('.btn-remove-progress');
        const addBtn       = actions.querySelector('.btn-add-progress');
        const numberInput  = actions.querySelector('input');
        const actionUrl    = actions.dataset.action;
        const card         = actions.closest('.goal-card');
        const progressBar  = card.querySelector('.progress-bar');
        const valueDisplay = card.querySelector('.goal-data span');

        const getValues = () => {
            const match = valueDisplay.textContent.match(/(\d+(?:\.\d+)?)\s*\/\s*(\d+(?:\.\d+)?)/);
            return match ? { current: parseFloat(match[1]), target: parseFloat(match[2]) } : { current: 0, target: 1 };
        };

        const updateDisplay = (newCurrent, target) => {
            progressBar.style.width = Math.min((newCurrent / target) * 100, 100) + '%';
            const parts = valueDisplay.textContent.trim().split(' ');
            const unit  = parts[parts.length - 1];
            valueDisplay.textContent = newCurrent + ' / ' + target + ' ' + unit;

            if (newCurrent >= target) {
                card.classList.remove('in-progress', 'not-started');
                card.classList.add('completed');
                card.querySelector('.status').textContent = 'Completed';
                card.querySelector('.status').className   = 'status status-completed';
                actions.style.display = 'none';
            } else if (newCurrent > 0) {
                card.classList.remove('completed', 'not-started');
                card.classList.add('in-progress');
                card.querySelector('.status').textContent = 'In Progress';
                card.querySelector('.status').className   = 'status status-in-progress';
            } else {
                card.classList.remove('completed', 'in-progress');
                card.classList.add('not-started');
                card.querySelector('.status').textContent = 'Not Started';
                card.querySelector('.status').className   = 'status status-not-started';
            }
            window.updateStats();
        };

        const sendProgress = (amount) => {
            const { current, target } = getValues();
            const newCurrent = Math.max(0, Math.min(current + amount, target));

            const formData = new FormData();
            formData.append('_token', csrf.content);
            formData.append('amount', amount);

            fetch(actionUrl, {
                method: 'POST',
                headers: { 'X-CSRF-TOKEN': csrf.content },
                body: formData
            }).then(() => updateDisplay(newCurrent, target));
        };

        removeBtn.onclick = e => { e.preventDefault(); sendProgress(-(parseFloat(numberInput.value) || 1)); };
        addBtn.onclick    = e => { e.preventDefault(); sendProgress( parseFloat(numberInput.value) || 1);  };

        numberInput.oninput = function () { if (this.value < 0.1) this.value = 0.1; };
        numberInput.onclick = function () { this.select(); };
    });

    filterBtns.forEach(btn => {
        btn.onclick = () => {
            filterBtns.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');

            const filter = btn.textContent.trim();
            document.querySelectorAll('.goal-card').forEach(card => {
                if      (filter === 'All')                                              card.style.display = 'block';
                else if (filter === 'Completed'   && card.classList.contains('completed'))   card.style.display = 'block';
                else if (filter === 'In Progress' && card.classList.contains('in-progress')) card.style.display = 'block';
                else if (filter === 'Not Started' && card.classList.contains('not-started')) card.style.display = 'block';
                else    card.style.display = 'none';
            });
        };
    });

    updateStats();
});