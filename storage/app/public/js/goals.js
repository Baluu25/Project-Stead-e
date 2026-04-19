document.addEventListener('DOMContentLoaded', function () {

    // icons of categories
    const categoryIcons = {
        'general':  ['fa-solid fa-apple-whole','fa-solid fa-carrot','fa-solid fa-lemon','fa-solid fa-bowl-food','fa-solid fa-mug-saucer','fa-solid fa-burger','fa-solid fa-fish','fa-solid fa-egg','fa-solid fa-droplet','fa-solid fa-wine-bottle'],
        'fitness':  ['fa-solid fa-dumbbell','fa-solid fa-person-running','fa-solid fa-person-walking','fa-solid fa-bicycle','fa-solid fa-heart-pulse','fa-solid fa-fire','fa-solid fa-stopwatch','fa-solid fa-shoe-prints','fa-solid fa-weight-scale','fa-solid fa-person-swimming'],
        'health':   ['fa-solid fa-brain','fa-solid fa-heart','fa-solid fa-spa','fa-regular fa-face-smile','fa-solid fa-feather','fa-solid fa-leaf','fa-solid fa-om','fa-solid fa-cloud','fa-solid fa-wind','fa-regular fa-moon'],
        'career':   ['fa-solid fa-book','fa-solid fa-book-open','fa-solid fa-graduation-cap','fa-solid fa-pencil','fa-solid fa-pen','fa-solid fa-brain','fa-solid fa-lightbulb','fa-solid fa-microscope','fa-solid fa-flask','fa-solid fa-language'],
        'learning': ['fa-solid fa-briefcase','fa-solid fa-laptop','fa-solid fa-computer','fa-solid fa-clock','fa-solid fa-calendar-check','fa-solid fa-chart-line','fa-solid fa-chart-simple','fa-solid fa-envelope','fa-solid fa-users','fa-solid fa-mug-hot']
    };

    // DOM
    const category     = document.getElementById('category');
    const iconGrid     = document.getElementById('icon-grid');
    const selectedIcon = document.getElementById('selected-icon');
    const iconInput    = document.getElementById('icon');
    const iconDisplay  = document.getElementById('selected-icon-display');
    const addBtn       = document.getElementById('addGoalButton');
    const popup        = document.getElementById('goalFormPopup');
    const closeBtn     = document.getElementById('closePopupBtn');
    const form         = document.getElementById('goal-form');
    const filterBtns   = document.querySelectorAll('.filter-btn');
    const csrf         = document.querySelector('meta[name="csrf-token"]');

    // summary stats
    window.updateStats = function () {
        const badges     = document.querySelectorAll('.stat-badge');
        const completed  = document.querySelectorAll('.goal-card.completed').length;
        const inProgress = document.querySelectorAll('.goal-card.in-progress').length;
        const notStarted = document.querySelectorAll('.goal-card.not-started').length;

        if (badges[0]) badges[0].textContent = completed  + ' Goals Completed';
        if (badges[1]) badges[1].textContent = inProgress + ' Goals In Progress';
        if (badges[2]) badges[2].textContent = notStarted + ' Goals Not Started';
    };

    // icon selector

    // set chosen icon
    function selectIcon(iconClass) {
        iconDisplay.className = iconClass;
        iconInput.value       = iconClass;
        iconGrid.style.display = 'none';

        selectedIcon.style.borderColor = '#25408F';
        setTimeout(function () { selectedIcon.style.borderColor = '#ddd'; }, 500);
    }

    // build icon grid for category selected
    function populateIcons(icons) {
        iconGrid.innerHTML = '';
        icons.forEach(function (iconClass) {
            const wrapper = document.createElement('div');
            const icon    = document.createElement('i');
            icon.className = iconClass;
            wrapper.appendChild(icon);
            wrapper.addEventListener('click', function () { selectIcon(iconClass); });
            iconGrid.appendChild(wrapper);
        });
        iconGrid.style.display = 'grid';
    }

    // toggle icon grid
    if (selectedIcon) {
        selectedIcon.addEventListener('click', function (e) {
            e.preventDefault();
            if (!category.value) return alert('Please select a category first.');
            if (!categoryIcons[category.value]) return alert('No icons for this category.');
            iconGrid.style.display = iconGrid.style.display === 'grid' ? 'none' : 'grid';
        });
    }

    // reload icons when category change
    if (category) {
        category.addEventListener('change', function () {
            if (category.value && categoryIcons[category.value]) {
                populateIcons(categoryIcons[category.value]);
                iconDisplay.className = 'fa-solid fa-bullseye';
                iconInput.value       = 'fa-solid fa-bullseye';
            } else {
                iconGrid.innerHTML    = '';
                iconGrid.style.display = 'none';
            }
        });
    }

    // close icon grid when clicked off
    document.addEventListener('click', function (e) {
        const selector = document.getElementById('icon-selector');
        if (selector && !selector.contains(e.target)) {
            iconGrid.style.display = 'none';
        }
    });

    // popup form
    function showPopup() {
        popup.style.display = 'block';
        if (form) form.reset();
        iconDisplay.className  = 'fa-solid fa-bullseye';
        iconInput.value        = 'fa-solid fa-bullseye';
        if (category) category.value = '';
        iconGrid.innerHTML     = '';
        iconGrid.style.display = 'none';
    }

    function hidePopup() {
        popup.style.display = 'none';
    }

    if (addBtn)   addBtn.addEventListener('click',   function (e) { e.preventDefault(); showPopup(); });
    if (closeBtn) closeBtn.addEventListener('click', function (e) { e.preventDefault(); hidePopup(); });
    if (popup)    popup.addEventListener('click',    function (e) { if (e.target === popup) hidePopup(); });

    // Close popup with esc
    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape' && popup && popup.style.display === 'block') hidePopup();
    });

    // form submit
    if (form) {
        form.addEventListener('submit', function (e) {
            e.preventDefault();
            fetch(form.action, {
                method: 'POST',
                headers: { 'X-CSRF-TOKEN': csrf ? csrf.content : '' },
                body: new FormData(form)
            })
            .then(function (response) {
                if (response.ok) {
                    hidePopup();
                    location.reload();
                } else {
                    alert('Could not save goal.');
                }
            })
            .catch(function () { alert('Network error.'); });
        });
    }

    // progress controls
    document.querySelectorAll('.goal-progress-actions').forEach(function (actions) {
        const removeBtn    = actions.querySelector('.btn-remove-progress');
        const addBtn       = actions.querySelector('.btn-add-progress');
        const numberInput  = actions.querySelector('input');
        const actionUrl    = actions.dataset.action;
        const card         = actions.closest('.goal-card');
        const progressBar  = card.querySelector('.progress-bar');
        const valueDisplay = card.querySelector('.goal-data span');

        // read the values of target
        function getValues() {
            const match = valueDisplay.textContent.match(/(\d+(?:\.\d+)?)\s*\/\s*(\d+(?:\.\d+)?)/);
            return match
                ? { current: parseFloat(match[1]), target: parseFloat(match[2]) }
                : { current: 0, target: 1 };
        }

        // Update goal card
        function updateDisplay(newCurrent, target) {
            progressBar.style.width = Math.min((newCurrent / target) * 100, 100) + '%';

            // Rebuild value text
            const parts = valueDisplay.textContent.trim().split(' ');
            const unit  = parts[parts.length - 1];
            valueDisplay.textContent = newCurrent + ' / ' + target + ' ' + unit;

            const statusEl = card.querySelector('.status');

            if (newCurrent >= target) {
                card.className = card.className.replace(/\b(in-progress|not-started)\b/g, '').trim();
                card.classList.add('completed');
                statusEl.textContent = 'Completed';
                statusEl.className   = 'status status-completed';
                actions.style.display = 'none';
            } else if (newCurrent > 0) {
                card.className = card.className.replace(/\b(completed|not-started)\b/g, '').trim();
                card.classList.add('in-progress');
                statusEl.textContent = 'In Progress';
                statusEl.className   = 'status status-in-progress';
            } else {
                card.className = card.className.replace(/\b(completed|in-progress)\b/g, '').trim();
                card.classList.add('not-started');
                statusEl.textContent = 'Not Started';
                statusEl.className   = 'status status-not-started';
            }

            window.updateStats();
        }

        // send progress to server
        function sendProgress(amount) {
            const { current, target } = getValues();
            const newCurrent = Math.max(0, Math.min(current + amount, target));

            const formData = new FormData();
            formData.append('_token', csrf.content);
            formData.append('amount', amount);

            fetch(actionUrl, {
                method: 'POST',
                headers: { 'X-CSRF-TOKEN': csrf.content },
                body: formData
            }).then(function () { updateDisplay(newCurrent, target); });
        }

        removeBtn.addEventListener('click', function (e) {
            e.preventDefault();
            sendProgress(-(parseFloat(numberInput.value) || 1));
        });

        addBtn.addEventListener('click', function (e) {
            e.preventDefault();
            sendProgress(parseFloat(numberInput.value) || 1);
        });

        numberInput.addEventListener('input', function () {
            if (this.value < 0.1) this.value = 0.1;
        });

        numberInput.addEventListener('click', function () { this.select(); });
    });

    // filter tabs
    filterBtns.forEach(function (btn) {
        btn.addEventListener('click', function () {
            filterBtns.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');

            const filter = btn.textContent.trim();

            document.querySelectorAll('.goal-card').forEach(function (card) {
                const show =
                    filter === 'All'          ||
                    (filter === 'Completed'   && card.classList.contains('completed'))   ||
                    (filter === 'In Progress' && card.classList.contains('in-progress')) ||
                    (filter === 'Not Started' && card.classList.contains('not-started'));

                card.style.display = show ? 'block' : 'none';
            });
        });
    });

    // upodate stats on load
    window.updateStats();
});
