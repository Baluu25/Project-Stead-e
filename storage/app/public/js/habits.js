document.addEventListener('DOMContentLoaded', function () {

    // icons by category
    const categoryIcons = {
        'Nutrition': 
        ['fa-solid fa-apple-whole','fa-solid fa-carrot','fa-solid fa-lemon','fa-solid fa-bowl-food','fa-solid fa-mug-saucer','fa-solid fa-burger','fa-solid fa-fish','fa-solid fa-egg','fa-solid fa-droplet','fa-solid fa-wine-bottle','fa-solid fa-utensils','fa-solid fa-plate-wheat','fa-solid fa-cookie','fa-solid fa-cake-candles','fa-solid fa-mug-hot'],
        'Fitness': 
        ['fa-solid fa-dumbbell','fa-solid fa-person-running','fa-solid fa-person-walking','fa-solid fa-bicycle','fa-solid fa-heart-pulse','fa-solid fa-fire','fa-solid fa-stopwatch','fa-solid fa-shoe-prints','fa-solid fa-weight-scale','fa-solid fa-person-swimming','fa-solid fa-person-biking','fa-solid fa-person-hiking','fa-solid fa-futbol','fa-solid fa-basketball','fa-solid fa-dog','fa-solid fa-heartbeat'],
        'Mindfulness': 
        ['fa-solid fa-brain','fa-solid fa-heart','fa-solid fa-spa','fa-regular fa-face-smile','fa-solid fa-feather','fa-solid fa-leaf','fa-solid fa-om','fa-solid fa-cloud','fa-solid fa-wind','fa-regular fa-moon','fa-solid fa-dog','fa-solid fa-cat','fa-solid fa-dove','fa-regular fa-sun','fa-solid fa-tree'],
        'Study': 
        ['fa-solid fa-book','fa-solid fa-book-open','fa-solid fa-graduation-cap','fa-solid fa-pencil','fa-solid fa-pen','fa-solid fa-brain','fa-solid fa-lightbulb','fa-solid fa-microscope','fa-solid fa-flask','fa-solid fa-language','fa-solid fa-calculator','fa-solid fa-glasses','fa-solid fa-ruler','fa-solid fa-chalkboard','fa-solid fa-school'],
        'Work': 
        ['fa-solid fa-briefcase','fa-solid fa-laptop','fa-solid fa-computer','fa-solid fa-clock','fa-solid fa-calendar-check','fa-solid fa-chart-line','fa-solid fa-chart-simple','fa-solid fa-envelope','fa-solid fa-users','fa-solid fa-mug-hot','fa-solid fa-building','fa-solid fa-phone','fa-solid fa-file-lines','fa-solid fa-folder','fa-solid fa-print']
    };

    // DOM
    const el = {
        category:     document.getElementById('category'),
        iconGrid:     document.getElementById('icon-grid'),
        selectedIcon: document.getElementById('selected-icon'),
        iconInput:    document.getElementById('icon'),
        iconDisplay:  document.getElementById('selected-icon-display'),
        addBtn:       document.getElementById('addHabitBtn'),
        popup:        document.getElementById('habitFormPopup'),
        closeBtn:     document.getElementById('closePopupBtn'),
        form:         document.getElementById('habit-form'),
        habitsList:   document.getElementById('habits-list'),
        formTitle:    document.getElementById('habitFormTitle'),
        submitBtn:    document.getElementById('habitFormSubmitBtn'),
        habitIdInput: document.getElementById('habit-id'),
        searchInput:  document.getElementById('habit-name'),
        goalSelect:   document.getElementById('goal_id'),
        unitInput:    document.getElementById('unit'),
        frequencySelect:     document.getElementById('frequency'),
        scheduledDaysGroup:  document.getElementById('scheduled-days-group'),
        weeklyPicker:        document.getElementById('weekly-picker'),
        monthlyPicker:       document.getElementById('monthly-picker'),
    };

    let editMode      = false;
    let currentEditId = null;
    let habitsCache   = [];

    // setup
    if (el.goalSelect && window.userGoals && window.userGoals.length > 0) {
        window.userGoals.forEach(function (goal) {
            const option       = document.createElement('option');
            option.value       = goal.id;
            option.textContent = goal.title;
            el.goalSelect.appendChild(option);
        });
    }

    // api helpers
    function getCsrf() {
        return document.querySelector('meta[name="csrf-token"]').content;
    }

    const api = {
        get: function (url) {
            return fetch(url, {
                headers: { 'Accept': 'application/json', 'X-CSRF-TOKEN': getCsrf() }
            }).then(function (res) {
                return res.ok ? res.json() : Promise.reject('HTTP ' + res.status);
            });
        },

        post: function (url, data) {
            return fetch(url, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json', 'Accept': 'application/json', 'X-CSRF-TOKEN': getCsrf() },
                body: JSON.stringify(data)
            }).then(res => res.json());
        },

        put: function (url, data) {
            return fetch(url, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json', 'Accept': 'application/json', 'X-CSRF-TOKEN': getCsrf() },
                body: JSON.stringify(data)
            }).then(res => res.json());
        },

        delete: function (url) {
            return fetch(url, {
                method: 'DELETE',
                headers: { 'Accept': 'application/json', 'X-CSRF-TOKEN': getCsrf() }
            }).then(function (res) { return res.status === 204 ? null : res.json(); });
        }
    };

    // for safe habit naming
    function escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    // icon selector
    function selectIcon(iconClass) {
        el.iconDisplay.className  = iconClass;
        el.iconInput.value        = iconClass;
        el.iconGrid.style.display = 'none';

        el.selectedIcon.style.borderColor = '#25408F';
        setTimeout(function () { el.selectedIcon.style.borderColor = '#ddd'; }, 500);
    }

    // build icon grid by category
    function populateIcons(icons) {
        el.iconGrid.innerHTML = '';
        icons.forEach(function (iconClass) {
            const wrapper  = document.createElement('div');
            const icon     = document.createElement('i');
            icon.className = iconClass;
            wrapper.appendChild(icon);
            wrapper.addEventListener('click', function () { selectIcon(iconClass); });
            el.iconGrid.appendChild(wrapper);
        });
        el.iconGrid.style.display = 'grid';
    }

    if (el.selectedIcon) {
        el.selectedIcon.addEventListener('click', function (e) {
            e.preventDefault();
            if (!el.category.value) return alert('Please select a category first');
            if (!categoryIcons[el.category.value]) return alert('Please select a valid category');
            el.iconGrid.style.display = el.iconGrid.style.display === 'grid' ? 'none' : 'grid';
        });
    }

    if (el.category) {
        el.category.addEventListener('change', function () {
            if (el.category.value && categoryIcons[el.category.value]) {
                populateIcons(categoryIcons[el.category.value]);
                el.iconDisplay.className = 'fa-solid fa-smile';
                el.iconInput.value       = 'fa-solid fa-smile';
            } else {
                el.iconGrid.innerHTML    = '';
                el.iconGrid.style.display = 'none';
            }
        });
    }

    // close icon grid when clicked off
    document.addEventListener('click', function (e) {
        const selector = document.getElementById('icon-selector');
        if (selector && !selector.contains(e.target)) {
            el.iconGrid.style.display = 'none';
        }
    });

    // update day picker visibility
    function updateDayPickerVisibility(frequency) {
        if (!el.scheduledDaysGroup) return;

        if (frequency === 'weekly') {
            el.scheduledDaysGroup.style.display = 'block';
            el.weeklyPicker.style.display       = 'block';
            el.monthlyPicker.style.display      = 'none';
        } else if (frequency === 'monthly') {
            el.scheduledDaysGroup.style.display = 'block';
            el.weeklyPicker.style.display       = 'none';
            el.monthlyPicker.style.display      = 'block';
        } else {
            // hide if empty
            el.scheduledDaysGroup.style.display = 'none';
            el.weeklyPicker.style.display       = 'none';
            el.monthlyPicker.style.display      = 'none';
            clearDaySelections();
        }
    }

    // clear selections
    function clearDaySelections() {
        document.querySelectorAll('.day-toggle-btn').forEach(function (btn) {
            btn.classList.remove('selected');
        });
    }

    // pre-select specific days
    function setSelectedDays(days) {
        clearDaySelections();
        if (!days || !days.length) return;
        days.forEach(function (day) {
            const btn = document.querySelector(`.day-toggle-btn[data-day="${day}"]`);
            if (btn) btn.classList.add('selected');
        });
    }

    // read the current day selected
    function getSelectedDays() {
        const selected = [];
        document.querySelectorAll('.day-toggle-btn.selected').forEach(function (btn) {
            selected.push(parseInt(btn.dataset.day, 10));
        });
        return selected;
    }

    // toggle day button
    document.addEventListener('click', function (e) {
        const btn = e.target.closest('.day-toggle-btn');
        if (!btn) return;
        e.preventDefault();
        btn.classList.toggle('selected');
    });

    if (el.frequencySelect) {
        el.frequencySelect.addEventListener('change', function () {
            updateDayPickerVisibility(this.value);
        });
    }

    // render habits list
    function renderHabits(habits) {
        if (!habits || habits.length === 0) {
            el.habitsList.innerHTML = `
                <div id="placeholder-container">
                    <img src="images/placeholder-img.png" alt="placeholder" id="placeholder-img">
                    <p id="placeholder-msg">No habits found</p>
                </div>`;
            return;
        }

        el.habitsList.innerHTML = habits.map(function (h) {
            const statusClass = h.is_active ? 'active'  : 'paused';
            const statusText  = h.is_active ? 'Active'  : 'Paused';
            const goalName    = h.goal_name ? escapeHtml(h.goal_name) : '—';

            return `
                <div class="habit-item" data-id="${h.id}">
                    <div class="habit-icon"><i class="${h.icon || 'fa-solid fa-smile'}"></i></div>
                    <div class="habit-name">${escapeHtml(h.name)}</div>
                    <div class="habit-goal">${goalName}</div>
                    <div class="habit-frequency">${h.frequency}</div>
                    <div class="habit-target">${h.target_count ?? 1}${h.unit ? ' ' + escapeHtml(h.unit) : ''}</div>
                    <div class="habit-status"><span class="status-${statusClass}">${statusText}</span></div>
                    <div class="habit-actions">
                        <button class="edit-btn"   data-id="${h.id}"><i class="fa-solid fa-edit"></i></button>
                        <button class="delete-btn" data-id="${h.id}"><i class="fa-solid fa-trash"></i></button>
                    </div>
                </div>`;
        }).join('');

        // edit and delete buttons
        document.querySelectorAll('.delete-btn').forEach(function (btn) {
            btn.addEventListener('click', function () { deleteHabit(btn.dataset.id); });
        });
        document.querySelectorAll('.edit-btn').forEach(function (btn) {
            btn.addEventListener('click', function () {
                const habit = habitsCache.find(h => h.id === parseInt(btn.dataset.id, 10));
                if (habit) showPopup(habit);
            });
        });
    }

    // load/delete habits
    function loadHabits() {
        api.get('/api/habits')
            .then(function (habits) {
                habitsCache = habits;
                renderHabits(habits);
            })
            .catch(function () {
                if (el.habitsList) {
                    el.habitsList.innerHTML = `
                        <div id="placeholder-container">
                            <p id="placeholder-msg">Could not load habits. Please refresh the page.</p>
                        </div>`;
                }
            });
    }

    function deleteHabit(id) {
        if (!confirm('Are you sure you want to delete this habit?')) return;
        api.delete('/api/habits/' + id)
            .then(loadHabits)
            .catch(function () { alert('Error deleting habit'); });
    }

    // filter habits by name
    function filterHabits() {
        const query = el.searchInput.value.trim().toLowerCase();
        if (!query) {
            renderHabits(habitsCache);
            return;
        }
        const filtered = habitsCache.filter(h => h.name.toLowerCase().includes(query));
        renderHabits(filtered);
    }

    // popup form
    function showPopup(habit) {
        editMode      = habit != null;
        currentEditId = habit ? habit.id : null;

        if (el.form) el.form.reset();
        el.iconDisplay.className  = 'fa-solid fa-smile';
        el.iconInput.value        = 'fa-solid fa-smile';
        el.category.value         = '';
        el.iconGrid.innerHTML     = '';
        el.iconGrid.style.display = 'none';
        el.habitIdInput.value     = '';
        el.unitInput.value        = '';
        el.goalSelect.value       = '';

        clearDaySelections();
        updateDayPickerVisibility('');

        if (habit) {
            document.getElementById('name').value          = habit.name         || '';
            document.getElementById('description').value   = habit.description  || '';
            document.getElementById('frequency').value     = habit.frequency    || '';
            document.getElementById('target_count').value  = habit.target_count ?? 1;
            document.getElementById('unit').value          = habit.unit         || '';

            el.category.value  = habit.category || '';
            if (habit.category && categoryIcons[habit.category]) {
                populateIcons(categoryIcons[habit.category]);
            }
            selectIcon(habit.icon || 'fa-solid fa-smile');

            el.goalSelect.value       = habit.goal_id || '';
            el.habitIdInput.value     = habit.id;
            el.formTitle.textContent  = 'Edit habit';
            el.submitBtn.textContent  = 'Save Changes';

            updateDayPickerVisibility(habit.frequency);
            if (habit.scheduled_days && habit.scheduled_days.length) {
                setSelectedDays(habit.scheduled_days);
            }

        } else {
            el.formTitle.textContent  = 'Add habit';
            el.submitBtn.textContent  = 'Add Habit';
        }

        el.popup.style.display = 'block';
    }

    function hidePopup() {
        el.popup.style.display    = 'none';
        editMode                  = false;
        currentEditId             = null;
        el.habitIdInput.value     = '';
        el.goalSelect.value       = '';
        el.unitInput.value        = '';
        el.formTitle.textContent  = 'Add habit';
        el.submitBtn.textContent  = 'Add Habit';
    }

    if (el.addBtn && el.popup && el.closeBtn) {
        el.addBtn.addEventListener('click',   function (e) { e.preventDefault(); showPopup(); });
        el.closeBtn.addEventListener('click', function (e) { e.preventDefault(); hidePopup(); });
        el.popup.addEventListener('click',    function (e) { if (e.target === el.popup) hidePopup(); });
        document.addEventListener('keydown',  function (e) { if (e.key === 'Escape' && el.popup.style.display === 'block') hidePopup(); });
    }

    // form submit
    if (el.form) {
        el.form.addEventListener('submit', function (e) {
            e.preventDefault();

            const frequency = document.getElementById('frequency').value;

            // collect scheduled days
            let scheduledDays = null;
            if (frequency === 'weekly' || frequency === 'monthly') {
                scheduledDays = getSelectedDays();
                if (scheduledDays.length === 0) {
                    alert('Please select at least one day for your ' + frequency + ' habit.');
                    return;
                }
            }

            const data = {
                name:         document.getElementById('name').value,
                description:  document.getElementById('description').value,
                category:     document.getElementById('category').value,
                frequency:    document.getElementById('frequency').value,
                target_count: document.getElementById('target_count').value || null,
                icon:         document.getElementById('icon').value,
                goal_id:      el.goalSelect.value || null,
                unit:         document.getElementById('unit').value || null,
                scheduled_days: scheduledDays
            };

            if (editMode && currentEditId) {
                api.put('/api/habits/' + currentEditId, data)
                    .then(function (habit) {
                        if (habit.id) {
                            hidePopup();
                            loadHabits();
                            alert('Habit updated successfully!');
                        } else if (habit.errors) {
                            alert('Validation error. Please check your inputs.');
                        }
                    })
                    .catch(function () { alert('Error updating habit'); });
            } else {
                api.post('/api/habits', data)
                    .then(function (habit) {
                        if (habit.id || habit.success) {
                            hidePopup();
                            loadHabits();
                            alert('Habit added successfully!');
                            el.form.reset();
                        } else if (habit.errors) {
                            alert('Please check the form for errors');
                        }
                    })
                    .catch(function () { alert('Error adding habit'); });
            }
        });
    }

    // restore icon display
    if (el.iconInput.value && el.iconInput.value !== 'fa-solid fa-smile') {
        el.iconDisplay.className = el.iconInput.value;
    }

    // search input
    if (el.searchInput) {
        el.searchInput.addEventListener('input', filterHabits);
    }

    // Load habits on page
    loadHabits();
});
