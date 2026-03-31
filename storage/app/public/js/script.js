document.addEventListener('DOMContentLoaded', function() {
  // Slideshow
  const slides = document.querySelectorAll('.slide');
  const indicators = document.querySelectorAll('.indicator');
  let currentSlide = 0;
  const slideInterval = 5000;
  let slideTimer;
  
  showSlide(currentSlide);
  startSlideShow();
  
  indicators.forEach(indicator => {
    indicator.addEventListener('click', function() {
      const index = parseInt(this.getAttribute('data-index'));
      showSlide(index);
      resetSlideShow();
    });
  });
  
  function showSlide(index) {
    if (slides.length === 0) return;
    slides.forEach(slide => {
      slide.classList.remove('active');
    });
    
    indicators.forEach(indicator => {
      indicator.classList.remove('active');
    });
    

    slides[index].classList.add('active');
    indicators[index].classList.add('active');
    currentSlide = index;
  }
  

  function nextSlide() {
    currentSlide = (currentSlide + 1) % slides.length;
    showSlide(currentSlide);
  }
  

  function startSlideShow() {
    slideTimer = setInterval(nextSlide, slideInterval);
  }

  function resetSlideShow() {
    clearInterval(slideTimer);
    startSlideShow();
  }

  // Smooth scrolling for navigation links
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();
      const targetId = this.getAttribute('href');
      if (targetId === '#') return;
      
      const targetElement = document.querySelector(targetId);
      if (targetElement) {
        window.scrollTo({
          top: targetElement.offsetTop - 100,
          behavior: 'smooth'
        });
      }
    });
  });

  const reviewCards = document.querySelectorAll('.review-card');
  
  const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
  };
  
  const observer = new IntersectionObserver(function(entries) {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.style.opacity = '1';
        entry.target.style.transform = 'translateY(0)';
      }
    });
  }, observerOptions);
  

  reviewCards.forEach(card => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(20px)';
    card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
    observer.observe(card);
  });
  

  reviewCards.forEach(card => {
    card.addEventListener('click', function() {
      this.style.transform = 'scale(0.98)';
      setTimeout(() => {
        this.style.transform = '';
      }, 150);
    });
  });
});


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
