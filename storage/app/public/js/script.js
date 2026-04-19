document.addEventListener('DOMContentLoaded', function () {

    // slideshow
    const slides     = document.querySelectorAll('.slide');
    const indicators = document.querySelectorAll('.indicator');
    let currentSlide = 0;
    let slideTimer;
    const SLIDE_INTERVAL = 5000;

    // show slide by index
    function showSlide(index) {
        if (slides.length === 0) return;

        slides.forEach(slide => slide.classList.remove('active'));
        indicators.forEach(indicator => indicator.classList.remove('active'));

        slides[index].classList.add('active');
        indicators[index].classList.add('active');
        currentSlide = index;
    }

    // advance to next slide
    function nextSlide() {
        currentSlide = (currentSlide + 1) % slides.length;
        showSlide(currentSlide);
    }

    // auto timer start
    function startSlideShow() {
        slideTimer = setInterval(nextSlide, SLIDE_INTERVAL);
    }

    // restart timer
    function resetSlideShow() {
        clearInterval(slideTimer);
        startSlideShow();
    }

    // indicator dots
    indicators.forEach(indicator => {
        indicator.addEventListener('click', function () {
            const index = parseInt(this.getAttribute('data-index'));
            showSlide(index);
            resetSlideShow();
        });
    });

    showSlide(currentSlide);
    startSlideShow();

    // smooth scroll
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

    // review cards
    const reviewCards = document.querySelectorAll('.review-card');

    // cards start hidden
    reviewCards.forEach(card => {
        card.style.opacity    = '0';
        card.style.transform  = 'translateY(20px)';
        card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
    });

    // when element enters viewport
    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity   = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    });

    reviewCards.forEach(card => observer.observe(card));

    // review card effect
    reviewCards.forEach(card => {
        card.addEventListener('click', function () {
            this.style.transform = 'scale(0.98)';
            setTimeout(() => { this.style.transform = ''; }, 150);
        });
    });
});
