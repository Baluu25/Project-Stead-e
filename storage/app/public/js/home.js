const datesLine = document.getElementById('datesLine');
const prevDateBtn = document.getElementById('prevDateBtn');
const nextDateBtn = document.getElementById('nextDateBtn');
const monthYearHeader = document.querySelector('.dates-header h2');
const todayBtn = document.getElementById('todayBtn');

    
    if (datesLine && prevDateBtn && nextDateBtn && monthYearHeader) {
        let currentDate = new Date();
        let selectedDate = new Date();
        
        function getDayName(date) {
            return ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'][date.getDay()];
        }
        
        function updateMonthYear() {
            const monthNames = ['January', 'February', 'March', 'April', 'May', 'June',
                              'July', 'August', 'September', 'October', 'November', 'December'];
            const month = monthNames[currentDate.getMonth()];
            const year = currentDate.getFullYear();
            monthYearHeader.textContent = `${month} ${year}`;
        }
        
        function updateDatesLine() {
            datesLine.innerHTML = '';
            const today = new Date();
            
            for (let i = -3; i <= 3; i++) {
                const date = new Date(currentDate);
                date.setDate(currentDate.getDate() + i);
                
                const dateItem = document.createElement('div');
                dateItem.className = 'date-item';
                
                const isToday = date.getDate() === today.getDate() && 
                               date.getMonth() === today.getMonth() && 
                               date.getFullYear() === today.getFullYear();
                
                const isSelected = date.getDate() === selectedDate.getDate() && 
                                  date.getMonth() === selectedDate.getMonth() && 
                                  date.getFullYear() === selectedDate.getFullYear();
                
                if (isToday) dateItem.classList.add('today');
                if (isSelected) dateItem.classList.add('selected');
                
                const dateNumber = document.createElement('div');
                dateNumber.className = 'date-number';
                dateNumber.textContent = date.getDate();
                
                const dateDay = document.createElement('div');
                dateDay.className = 'date-day';
                dateDay.textContent = getDayName(date);
                
                dateItem.appendChild(dateNumber);
                dateItem.appendChild(dateDay);
                
                dateItem.addEventListener('click', function() {
                    document.querySelectorAll('.date-item.selected').forEach(item => {
                        item.classList.remove('selected');
                    });
                    this.classList.add('selected');
                    selectedDate = new Date(date);
                });
                
                datesLine.appendChild(dateItem);
            }
        }
        
        prevDateBtn.addEventListener('click', function() {
            currentDate.setDate(currentDate.getDate() - 7);
            updateMonthYear();
            updateDatesLine();
        });
        
        nextDateBtn.addEventListener('click', function() {
            currentDate.setDate(currentDate.getDate() + 7);
            updateMonthYear();
            updateDatesLine();
        });
        
        if (todayBtn) {
            todayBtn.addEventListener('click', function() {
                const today = new Date();
                currentDate = new Date(today);
                selectedDate = new Date(today);
                updateMonthYear();
                updateDatesLine();
            });
        }
        
        updateMonthYear();
        updateDatesLine();
    }

function habitFetch(url, method) {
    return fetch(url, {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
        },
        body: method === 'POST' ? JSON.stringify({ habit_id: url.match(/\d+/) ? undefined : null }) : undefined
    }).then(r => r.json());
}

function updateHabitCard(habitId, completed, target) {
    const card      = document.querySelector(`.habit-item[data-habit-id="${habitId}"]`);
    const countEl   = document.querySelector(`.progress-current[data-habit-id="${habitId}"]`);
    const fillEl    = document.querySelector(`.progress-bar-fill[data-habit-id="${habitId}"]`);
    const actionsEl = card ? card.querySelector('.habit-actions') : null;

    if (!card) return;

    const safeTarget = target ?? 1;
    const percent = safeTarget > 0 ? Math.min(100, Math.round((completed / safeTarget) * 100)) : 0;
    const isDone  = percent >= 100;

    if (countEl) countEl.textContent = completed;
    if (fillEl)  fillEl.style.width = percent + '%';


    card.classList.toggle('completed', isDone);

    if (isDone) {
        if (actionsEl) actionsEl.remove();
        if (!card.querySelector('.habit-done-badge')) {
            const badge = document.createElement('div');
            badge.className = 'habit-done-badge';
            badge.innerHTML = '<i class="fa-solid fa-check"></i>';
            card.appendChild(badge);
        }
    }
}

document.addEventListener('click', function(e) {
    const addBtn = e.target.closest('.btn-add-progress');
    if (!addBtn) return;

    const habitId = addBtn.dataset.habitId;
    const card    = document.querySelector(`.habit-item[data-habit-id="${habitId}"]`);
    const target  = card ? parseInt(card.dataset.target, 10) : 1;

    fetch('/api/habit-completions', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
        },
        body: JSON.stringify({ habit_id: habitId })
    })
    .then(r => { if (!r.ok) throw new Error('HTTP ' + r.status); return r.json(); })
    .then(data => updateHabitCard(habitId, data.completed, data.target))
    .catch(() => alert('Could not record progress. Please try again.'));
});

document.addEventListener('click', function(e) {
    const removeBtn = e.target.closest('.btn-remove-progress');
    if (!removeBtn) return;

    const habitId = removeBtn.dataset.habitId;
    const card    = document.querySelector(`.habit-item[data-habit-id="${habitId}"]`);
    const target  = card ? parseInt(card.dataset.target, 10) : 1;

    fetch(`/api/habit-completions/${habitId}/today/last`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
        }
    })
    .then(r => { if (!r.ok) throw new Error('HTTP ' + r.status); return r.json(); })
    .then(data => updateHabitCard(habitId, data.completed, data.target))
    .catch(() => alert('Could not remove progress. Please try again.'));
});