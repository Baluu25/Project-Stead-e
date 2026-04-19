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

        // update month and year
        function updateMonthYear() {
            monthYearHeader.textContent =
                MONTH_NAMES[currentDate.getMonth()] + ' ' + currentDate.getFullYear();
        }

        // if two date objects fall on same day
        function isSameDay(a, b) {
            return a.getDate()     === b.getDate()  &&
                   a.getMonth()    === b.getMonth() &&
                   a.getFullYear() === b.getFullYear();
        }

        // 7 days centered on current day
        function updateDatesLine() {
            datesLine.innerHTML = '';
            const today = new Date();

            // 3 days before and after current
            for (let offset = -3; offset <= 3; offset++) {
                const date = new Date(currentDate);
                date.setDate(currentDate.getDate() + offset);

                const dateItem = document.createElement('div');
                dateItem.className = 'date-item';
                if (isSameDay(date, today))        dateItem.classList.add('today');
                if (isSameDay(date, selectedDate)) dateItem.classList.add('selected');

                const numberEl = document.createElement('div');
                numberEl.className   = 'date-number';
                numberEl.textContent = date.getDate();

                const dayEl = document.createElement('div');
                dayEl.className   = 'date-day';
                dayEl.textContent = DAY_NAMES[date.getDay()];

                dateItem.appendChild(numberEl);
                dateItem.appendChild(dayEl);

                // select date
                dateItem.addEventListener('click', function () {
                    document.querySelectorAll('.date-item.selected')
                            .forEach(item => item.classList.remove('selected'));
                    this.classList.add('selected');
                    selectedDate = new Date(date);
                });

                datesLine.appendChild(dateItem);
            }
        }

        // navigate calendar
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

        // back to current day
        if (todayBtn) {
            todayBtn.addEventListener('click', function () {
                currentDate  = new Date();
                selectedDate = new Date();
                updateMonthYear();
                updateDatesLine();
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

        if (countEl) countEl.textContent   = completed;
        if (fillEl)  fillEl.style.width    = percent + '%';

        card.classList.toggle('completed', isDone);

        if (isDone) {
            // Habit finished
            if (actionsEl) actionsEl.remove();
            if (!card.querySelector('.habit-done-badge')) {
                const badge       = document.createElement('div');
                badge.className   = 'habit-done-badge';
                badge.innerHTML   = '<i class="fa-solid fa-check"></i>';
                card.appendChild(badge);
            }
        } else {
            // Habit not done
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

    // update daily progress
    function updateProgressRing(percent) {
        const fill = document.querySelector('.progress-ring-fill');
        const text = document.querySelector('.progress-percentage');
        if (!fill || !text) return;

        const circumference = 2 * Math.PI * 54;
        const offset = circumference - (percent / 100) * circumference;
        fill.style.strokeDashoffset = offset;
        text.textContent = percent + '%';
    }

    // Update todays streak
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

    // fetch headers for api
    function getHeaders() {
        return {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
        };
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

    // select whole number
    document.addEventListener('click', function (e) {
        const input = e.target.closest('.habit-actions input[type="number"]');
        if (input) input.select();
    });

});
