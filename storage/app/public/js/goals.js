document.addEventListener('DOMContentLoaded', function () {

    // icons by category (Material Icons names)
    const categoryIcons = {
        'Nutrition':
        ['restaurant','local_dining','coffee','local_cafe','kitchen','cake','water_drop','wine_bar','fastfood','free_breakfast','emoji_food_beverage','set_meal','soup_kitchen','food_bank','ice_cream'],
        'Fitness':
        ['fitness_center','directions_run','directions_walk','pedal_bike','monitor_heart','local_fire_department','timer','nordic_walking','pool','hiking','sports_soccer','sports_basketball','sports','electric_bike','accessibility_new','sprint'],
        'Mindfulness':
        ['psychology','favorite','spa','sentiment_satisfied','cloud','air','bedtime','wb_sunny','park','nature','pets','self_improvement','nightlight','eco','brightness_5'],
        'Study':
        ['menu_book','book','school','edit','lightbulb','biotech','science','calculate','translate','visibility','auto_stories','quiz','history_edu','laptop_chromebook','straighten'],
        'Work':
        ['work','laptop','computer','schedule','event_available','trending_up','bar_chart','email','group','local_cafe','business','phone','description','folder','print']
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
    const filterBtns        = document.querySelectorAll('.filter-btn');
    const csrf              = document.querySelector('meta[name="csrf-token"]');
    const goalUnitSelect      = document.getElementById('unit');
    const goalCustomUnitGroup = document.getElementById('goal-custom-unit-group');
    const goalCustomUnitInput = document.getElementById('goal_custom_unit');
    const popupTitle          = document.getElementById('goalFormTitle');
    const popupSubtitle       = document.getElementById('goalFormSubtitle');
    const submitBtn           = document.getElementById('goalFormSubmitBtn');

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
    function selectIcon(iconName) {
        iconDisplay.textContent = iconName;
        iconInput.value         = iconName;
        iconGrid.style.display  = 'none';

        selectedIcon.style.borderColor = '#25408F';
        setTimeout(function () { selectedIcon.style.borderColor = '#ddd'; }, 500);
    }

    // build icon grid for category selected
    function populateIcons(icons) {
        iconGrid.innerHTML = '';
        icons.forEach(function (iconName) {
            const wrapper = document.createElement('div');
            const icon    = document.createElement('span');
            icon.className   = 'material-icons';
            icon.textContent = iconName;
            wrapper.appendChild(icon);
            wrapper.addEventListener('click', function () { selectIcon(iconName); });
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
                iconDisplay.textContent = 'sports';
                iconInput.value         = 'sports';
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

    // show/hide custom unit field
    if (goalUnitSelect && goalCustomUnitGroup) {
        goalUnitSelect.addEventListener('change', function () {
            goalCustomUnitGroup.style.display = goalUnitSelect.value === 'custom' ? 'block' : 'none';
            if (goalUnitSelect.value !== 'custom' && goalCustomUnitInput) {
                goalCustomUnitInput.value = '';
            }
        });
    }

    // popup form
    function showPopup(goalData) {
        popup.style.display = 'block';

        if (goalData) {
            popupTitle.textContent    = 'Edit Goal';
            popupSubtitle.textContent = 'Update your goal details';
            submitBtn.textContent     = 'Edit Goal';
            form.dataset.mode   = 'edit';
            form.dataset.goalId = goalData.id;

            document.getElementById('title').value        = goalData.title;
            document.getElementById('description').value  = goalData.description;
            document.getElementById('category').value     = goalData.category;
            document.getElementById('target_value').value = goalData.targetValue;
            document.getElementById('deadline').value     = goalData.deadline;

            const standardUnits = ['times', 'days', 'km', 'books', 'minutes'];
            if (standardUnits.includes(goalData.unit)) {
                goalUnitSelect.value              = goalData.unit;
                goalCustomUnitGroup.style.display = 'none';
                goalCustomUnitInput.value         = '';
            } else {
                goalUnitSelect.value              = 'custom';
                goalCustomUnitGroup.style.display = 'block';
                goalCustomUnitInput.value         = goalData.unit;
            }

            iconDisplay.textContent = goalData.icon;
            iconInput.value         = goalData.icon;
            iconGrid.innerHTML     = '';
            iconGrid.style.display = 'none';
            if (categoryIcons[goalData.category]) {
                populateIcons(categoryIcons[goalData.category]);
                iconGrid.style.display = 'none';
            }
        } else {
            popupTitle.textContent    = 'Add Goal';
            popupSubtitle.textContent = 'Add a new goal to track your progress';
            submitBtn.textContent     = 'Add Goal';
            form.dataset.mode = 'add';
            delete form.dataset.goalId;

            if (form) form.reset();
            iconDisplay.textContent = 'sports';
            iconInput.value         = 'sports';
            if (category) category.value = '';
            iconGrid.innerHTML     = '';
            iconGrid.style.display = 'none';
            if (goalCustomUnitGroup) goalCustomUnitGroup.style.display = 'none';
            if (goalCustomUnitInput) goalCustomUnitInput.value = '';
        }
    }

    function hidePopup() {
        popup.style.display = 'none';
        if (goalCustomUnitGroup) goalCustomUnitGroup.style.display = 'none';
        if (goalCustomUnitInput) goalCustomUnitInput.value = '';
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

            const fd = new FormData(form);

            if (goalUnitSelect && goalUnitSelect.value === 'custom' && goalCustomUnitInput) {
                const customText = goalCustomUnitInput.value.trim();
                if (!customText) {
                    alert('Please enter a custom unit name.');
                return;
                }
                fd.set('unit', customText);
            }

            const isEdit = form.dataset.mode === 'edit';
            const url    = isEdit ? '/goals/' + form.dataset.goalId : form.action;

            if (isEdit) {
                fd.set('_method', 'PUT');
            }

            fetch(url, {
                method: 'POST',
                headers: { 'X-CSRF-TOKEN': csrf ? csrf.content : '' },
                body: fd
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

    // edit button
    document.querySelectorAll('.btn-edit').forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const card = btn.closest('.goal-card');
            showPopup({
                id:          card.dataset.id,
                title:       card.dataset.title,
                description: card.dataset.description,
                category:    card.dataset.category,
                targetValue: card.dataset.targetValue,
                unit:        card.dataset.unit,
                deadline:    card.dataset.deadline,
                icon:        card.dataset.icon,
            });
        });
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
