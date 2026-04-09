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
