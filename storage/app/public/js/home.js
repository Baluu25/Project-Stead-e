document.addEventListener('DOMContentLoaded', function () {

    // calendar
    const datesLine       = document.getElementById('datesLine');
    const prevDateBtn     = document.getElementById('prevDateBtn');
    const nextDateBtn     = document.getElementById('nextDateBtn');
    const monthYearHeader = document.querySelector('.dates-header h2');
    const todayBtn        = document.getElementById('todayBtn');

    if (datesLine && prevDateBtn && nextDateBtn && monthYearHeader) {
        let currentDate  = new Date();
        let selectedDate = new Date();

        const MONTH_NAMES = ['January','February','March','April','May','June',
                             'July','August','September','October','November','December'];
        const DAY_NAMES   = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];

        // day names
        const FULL_DAY_NAMES = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];

        function updateMonthYear() {
            monthYearHeader.textContent =
                MONTH_NAMES[currentDate.getMonth()] + ' ' + currentDate.getFullYear();
        }

        function isSameDay(a, b) {
            return a.getDate()     === b.getDate()  &&
                   a.getMonth()    === b.getMonth() &&
                   a.getFullYear() === b.getFullYear();
        }

        function updateDatesLine() {
            datesLine.innerHTML = '';
            const today = new Date();

            for (let offset = -3; offset <= 3; offset++) {
                const date = new Date(currentDate);
                date.setDate(currentDate.getDate() + offset);

            const dateItem = document.createElement('div');
                dateItem.className = 'date-item';
                if (isSameDay(date, today)) dateItem.classList.add('today');
                if (isSameDay(date, selectedDate)) dateItem.classList.add('selected');

                const numberEl = document.createElement('div');
                numberEl.className = 'date-number';
                numberEl.textContent = date.getDate();

                const dayEl = document.createElement('div');
                dayEl.className = 'date-day';
                dayEl.textContent = DAY_NAMES[date.getDay()];

                dateItem.appendChild(numberEl);
                dateItem.appendChild(dayEl);
        
                dateItem.addEventListener('click', function () {
                    // Remove selected class from all date items
                    document.querySelectorAll('.date-item').forEach(item => {
                        item.classList.remove('selected');
                    });
                    // Add selected class to clicked item
                    this.classList.add('selected');
                    // Update selectedDate
                    selectedDate = new Date(date);
                    // Fetch habits for the new date
                    fetchHabitsForDate(selectedDate);
                    // Scroll to center
                    setTimeout(() => {
                        this.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'center' });
                    }, 50);
                });

                datesLine.appendChild(dateItem);
            }
        }

        prevDateBtn.addEventListener('click', function () {
            currentDate.setDate(currentDate.getDate() - 7);
            updateMonthYear();
            updateDatesLine();
        });

        nextDateBtn.addEventListener('click', function () {
            currentDate.setDate(currentDate.getDate() + 7);
            updateMonthYear();
            updateDatesLine();
        });

        if (todayBtn) {
            todayBtn.addEventListener('click', function () {
                currentDate  = new Date();
                selectedDate = new Date();
                updateMonthYear();
                updateDatesLine();
                fetchHabitsForDate(selectedDate);
            });
        }

        updateMonthYear();
        updateDatesLine();
    }

    // habit card
    function updateHabitCard(habitId, completed, target) {
        const card      = document.querySelector(`.habit-item[data-habit-id="${habitId}"]`);
        const countEl   = document.querySelector(`.progress-current[data-habit-id="${habitId}"]`);
        const fillEl    = document.querySelector(`.progress-bar-fill[data-habit-id="${habitId}"]`);
        const actionsEl = card ? card.querySelector('.habit-actions') : null;
        if (!card) return;

        const safeTarget = target ?? 1;
        const percent    = safeTarget > 0 ? Math.min(100, Math.round((completed / safeTarget) * 100)) : 0;
        const isDone     = percent >= 100;

        if (countEl) countEl.textContent = completed;
        if (fillEl)  fillEl.style.width  = percent + '%';

        card.classList.toggle('completed', isDone);

        if (isDone) {
            if (actionsEl) actionsEl.remove();
            if (!card.querySelector('.habit-done-badge')) {
                const badge       = document.createElement('div');
                badge.className   = 'habit-done-badge';
                badge.innerHTML   = '<i class="fa-solid fa-check"></i>';
                card.appendChild(badge);
            }
        } else {
            const badge = card.querySelector('.habit-done-badge');
            if (badge) badge.remove();

            if (!actionsEl) {
                const newActions     = document.createElement('div');
                newActions.className = 'habit-actions';
                newActions.innerHTML = `
                    <button class="btn btn-remove-progress" data-habit-id="${habitId}">
                        <i class="fa-solid fa-minus"></i>
                    </button>
                    <input type="number" value="1" min="1">
                    <button class="btn btn-add-progress" data-habit-id="${habitId}">
                        <i class="fa-solid fa-plus"></i>
                    </button>
                `;
                card.appendChild(newActions);
            }
        }
    }

    // update progress
    function updateProgressRing(percent) {
        const fill = document.querySelector('.progress-ring-fill');
        const text = document.querySelector('.progress-percentage');
        if (!fill || !text) return;

        const circumference = 2 * Math.PI * 54;
        const offset = circumference - (percent / 100) * circumference;
        fill.style.strokeDashoffset = offset;
        text.textContent = percent + '%';
    }

    // update streak circle
    function updateTodayStreakCircle(hasCompletion) {
        const circles = document.querySelectorAll('.day-circle');
        if (!circles.length) return;

        const todayCircle = circles[circles.length - 1];
        if (hasCompletion) {
            todayCircle.classList.add('active');
            if (!todayCircle.querySelector('i')) {
                todayCircle.innerHTML = '<i class="fa-solid fa-check"></i>';
            }
        } else {
            todayCircle.classList.remove('active');
            todayCircle.innerHTML = '';
        }
    }

    // headers helper
    function getHeaders() {
        return {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
        };
    }

    // formatting date
    function formatDate(date) {
        const yyyy = date.getFullYear();
        const mm   = String(date.getMonth() + 1).padStart(2, '0');
        const dd   = String(date.getDate()).padStart(2, '0');
        return `${yyyy}-${mm}-${dd}`;
    }

    // fetch habits for date
    function fetchHabitsForDate(date) {
        const dateStr = formatDate(date);

        fetch(`/api/home?date=${dateStr}`, { headers: getHeaders() })
            .then(function (r) {
                if (!r.ok) throw new Error('HTTP ' + r.status);
                return r.json();
            })
            .then(function (data) {
                renderHabitsSection(data.habits, data.daily_progress_percent, data.is_today, date);
                updateProgressRing(data.daily_progress_percent);
            })
            .catch(function () {
               //fail
            });
    }

    // render habits section from api
    function renderHabitsSection(habits, progressPercent, isToday, date) {
        const heading = document.getElementById('habits-section-heading');
        const list    = document.getElementById('daily-habits-list');
        if (!list) return;

        // Update the section heading
        if (heading) {
            if (isToday) {
                heading.textContent = "Today's Habits";
            } else {
                const MONTH_SHORT = ['Jan','Feb','Mar','Apr','May','Jun',
                                     'Jul','Aug','Sep','Oct','Nov','Dec'];
                const DAY_SHORT   = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];
                heading.textContent =
                    DAY_SHORT[date.getDay()] + ', ' +
                    MONTH_SHORT[date.getMonth()] + ' ' +
                    date.getDate() + ' · Habits';
            }
        }

        // clear list
        list.innerHTML = '';

        if (!habits || habits.length === 0) {
            list.innerHTML = `
                <div class="no-habits-message">
                    <p>No habits scheduled for this day.</p>
                    <a href="/habits">Add your first habit</a>
                </div>`;
            return;
        }

        habits.forEach(function (habit) {
            const card = document.createElement('div');
            card.className = 'habit-item' + (habit.is_done ? ' completed' : '');
            card.dataset.habitId  = habit.id;
            card.dataset.completed = habit.completed;
            card.dataset.target   = habit.target_count;

            // icon
            const iconDiv = document.createElement('div');
            iconDiv.className = 'habit-icon';
            iconDiv.innerHTML = `<i class="fa-solid fa-${habit.icon}"></i>`;

            // info
            const infoDiv = document.createElement('div');
            infoDiv.className = 'habit-info';
            infoDiv.innerHTML = `
                <span class="habit-name">${habit.name}</span>
                <span class="habit-category badge">${habit.category}</span>`;

            // progress
            const progressDiv = document.createElement('div');
            progressDiv.className = 'habit-progress';
            progressDiv.innerHTML = `
                <div class="progress-numbers">
                    <span class="progress-current" data-habit-id="${habit.id}">${habit.completed}</span>
                    <span class="progress-separator">/</span>
                    <span class="progress-target">${habit.target_count}${habit.unit ? ' ' + habit.unit : ''}</span>
                </div>
                <div class="progress-bar-track">
                    <div class="progress-bar-fill" data-habit-id="${habit.id}" style="width: ${habit.percent}%"></div>
                </div>`;

            card.appendChild(iconDiv);
            card.appendChild(infoDiv);
            card.appendChild(progressDiv);

            // action buttons
            if (isToday && !habit.is_done) {
                const actionsDiv = document.createElement('div');
                actionsDiv.className = 'habit-actions';
                actionsDiv.innerHTML = `
                    <button class="btn btn-remove-progress" data-habit-id="${habit.id}">
                        <i class="fa-solid fa-minus"></i>
                    </button>
                    <input type="number" value="1" min="1">
                    <button class="btn btn-add-progress" data-habit-id="${habit.id}">
                        <i class="fa-solid fa-plus"></i>
                    </button>`;
                card.appendChild(actionsDiv);
            }

            // done badge
            if (habit.is_done) {
                const badge = document.createElement('div');
                badge.className = 'habit-done-badge';
                badge.innerHTML = '<i class="fa-solid fa-check"></i>';
                card.appendChild(badge);
            }

            list.appendChild(card);
        });
    }

    // add progress
    document.addEventListener('click', function (e) {
        const addBtn = e.target.closest('.btn-add-progress');
        if (!addBtn) return;

        const habitId  = addBtn.dataset.habitId;
        const card     = document.querySelector(`.habit-item[data-habit-id="${habitId}"]`);
        const input    = card ? card.querySelector('.habit-actions input[type="number"]') : null;
        const quantity = input ? (parseInt(input.value, 10) || 1) : 1;

        fetch('/api/habit-completions', {
            method: 'POST',
            headers: getHeaders(),
            body: JSON.stringify({ habit_id: habitId, quantity: quantity })
        })
        .then(function (r) {
            if (!r.ok) throw new Error('HTTP ' + r.status);
            return r.json();
        })
        .then(function (data) {
            updateHabitCard(habitId, data.completed, data.target);
            updateProgressRing(data.daily_progress_percent);
            updateTodayStreakCircle(data.has_completion_today);
        })
        .catch(function () {
            alert('Could not record progress. Please try again.');
        });
    });

    // remove progress
    document.addEventListener('click', function (e) {
        const removeBtn = e.target.closest('.btn-remove-progress');
        if (!removeBtn) return;

        const habitId = removeBtn.dataset.habitId;
        const card    = document.querySelector(`.habit-item[data-habit-id="${habitId}"]`);
        const input   = card ? card.querySelector('.habit-actions input[type="number"]') : null;
        const amount  = input ? (parseInt(input.value, 10) || 1) : 1;

        fetch(`/api/habit-completions/${habitId}/today/last`, {
            method: 'DELETE',
            headers: getHeaders(),
            body: JSON.stringify({ amount: amount })
        })
        .then(function (r) {
            if (!r.ok) throw new Error('HTTP ' + r.status);
            return r.json();
        })
        .then(function (data) {
            updateHabitCard(habitId, data.completed, data.target);
            updateProgressRing(data.daily_progress_percent);
            updateTodayStreakCircle(data.has_completion_today);
        })
        .catch(function () {
            alert('Could not remove progress. Please try again.');
        });
    });

    // number input
    document.addEventListener('input', function (e) {
        const input = e.target.closest('.habit-actions input[type="number"]');
        if (!input) return;
        const val = parseInt(input.value, 10);
        if (!val || val < 1) input.value = 1;
    });

    document.addEventListener('click', function (e) {
        const input = e.target.closest('.habit-actions input[type="number"]');
        if (input) input.select();
    });

});
