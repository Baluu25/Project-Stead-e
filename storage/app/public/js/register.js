// tracking current step
let currentStep = 1;
const totalSteps = 6;

// navigation

// validate current step, go to next step
function nextStep() {
    if (!validateStep(currentStep)) return;

    slideOut(currentStep, 'left', function () {
        currentStep++;
        slideIn(currentStep);
        updateProgress();
    });
}

// back to previous step
function prevStep() {
    if (currentStep <= 1) return;

    slideOut(currentStep, 'right', function () {
        currentStep--;
        slideIn(currentStep);
        updateProgress();
    });
}

// slide animation when out
function slideOut(step, direction, callback) {
    const card = document.getElementById('step' + step);
    card.style.opacity   = '0';
    card.style.transform = direction === 'left' ? 'translateX(-100%)' : 'translateX(100%)';

    setTimeout(function () {
        card.classList.remove('active');
        card.style.display = 'none';
        callback();
    }, 300);
}

// slide animation when in
function slideIn(step) {
    const card = document.getElementById('step' + step);
    if (!card) return;

    card.style.display = 'block';
    card.classList.add('active');

    setTimeout(function () {
        card.style.opacity   = '1';
        card.style.transform = 'translateX(0)';
    }, 10);
}

// step validation
function validateStep(step) {
    if (step === 1) {
        const name     = document.getElementById('name').value.trim();
        const username = document.getElementById('username').value.trim();
        const email    = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;

        if (!name)                        return alert('Please enter your name'),     false;
        if (!username)                    return alert('Please enter a username'),     false;
        if (!email || !email.includes('@')) return alert('Please enter a valid email'), false;
        if (password.length < 6)          return alert('Password must be at least 6 characters'), false;
        return true;
    }

    if (step === 2) {
        const selected = document.querySelector('input[name="gender"]:checked');
        if (!selected) return alert('Please select a gender'), false;
        return true;
    }

    if (step === 3) {
        const weight = document.getElementById('weight').value;
        if (!weight || weight < 30 || weight > 300)
            return alert('Please enter a valid weight (30–300 kg)'), false;
        return true;
    }

    if (step === 4) {
        const height = document.getElementById('height').value;
        if (!height || height < 100 || height > 250)
            return alert('Please enter a valid height (100–250 cm)'), false;
        return true;
    }

    if (step === 5) {
        const sleepTime = document.getElementById('sleep_time').value;
        const wakeTime  = document.getElementById('wake_time').value;
        if (!sleepTime || !wakeTime)
            return alert('Please enter both sleep and wake times'), false;
        return true;
    }

    if (step === 6) {
        const selected = document.querySelector('input[name="user_goal"]:checked');
        if (!selected) return alert('Please select a goal'), false;
        return true;
    }

    return true;
}

// progress bar
function updateProgress() {
    const progressBar = document.getElementById('progressBar');
    if (progressBar) {
        const percent = ((currentStep - 1) / (totalSteps - 1)) * 100;
        progressBar.style.width = percent + '%';
    }

    document.querySelectorAll('.step').forEach(function (dot, index) {
        dot.classList.toggle('active', index + 1 <= currentStep);
    });
}

// page setup
document.addEventListener('DOMContentLoaded', function () {

    for (let i = 2; i <= totalSteps; i++) {
        const card = document.getElementById('step' + i);
        if (card) {
            card.style.display = 'none';
            card.classList.remove('active');
        }
    }

    // selects option
    document.querySelectorAll('.option-card').forEach(function (card) {
        card.addEventListener('click', function () {
            const radio = this.querySelector('input[type="radio"]');
            if (!radio) return;

            document.querySelectorAll(`input[name="${radio.name}"]`).forEach(function (other) {
                if (other !== radio) {
                    other.checked = false;
                    other.closest('.option-card').classList.remove('checked');
                }
            });

            radio.checked = true;
            this.classList.add('checked');
        });
    });

    // Password toggle
    const toggleBtn = document.getElementById('togglePassword');
    if (toggleBtn) {
        toggleBtn.addEventListener('click', function () {
            const passwordInput = document.getElementById('password');
            if (!passwordInput) return;

            const isHidden = passwordInput.type === 'password';
            passwordInput.type = isHidden ? 'text' : 'password';
            this.innerHTML = isHidden
                ? '<i class="fa-solid fa-eye-slash" style="color:#000"></i>'
                : '<i class="fa-solid fa-eye" style="color:#000"></i>';
        });
    }
});

// HTML onclick attributes
window.nextStep = nextStep;
window.prevStep = prevStep;
