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