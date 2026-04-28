let currentStep = 1;
const totalSteps = 4;

function nextStep() {
    if (!validateStep(currentStep)) return;
    slideOut(currentStep, 'left', function () {
        currentStep++;
        slideIn(currentStep);
        updateProgress();
    });
}

function prevStep() {
    if (currentStep <= 1) return;
    slideOut(currentStep, 'right', function () {
        currentStep--;
        slideIn(currentStep);
        updateProgress();
    });
}

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

function validateStep(step) {
    if (step === 1) {
        const name     = document.getElementById('name').value.trim();
        const username = document.getElementById('username').value.trim();
        const email    = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;
        if (!name)                          return alert('Please enter your name'),                  false;
        if (!username)                      return alert('Please enter a username'),                 false;
        if (!email || !email.includes('@')) return alert('Please enter a valid email'),              false;
        if (password.length < 8)            return alert('Password must be at least 8 characters'), false;
        return true;
    }
    if (step === 2) {
        return true;
    }
    if (step === 3) {
        const selected = document.querySelector('input[name="user_goal"]:checked');
        if (!selected) return alert('Please select a goal'), false;
        return true;
    }
    if (step === 4) {
        const selected = document.querySelectorAll('input[name="preferred_categories[]"]:checked');
        if (selected.length === 0) return alert('Please select at least one focus area'), false;
        return true;
    }
    return true;
}

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

function submitFinalForm() {
    if (!validateStep(4)) return;
    document.getElementById('final_name').value      = document.getElementById('name').value;
    document.getElementById('final_username').value  = document.getElementById('username').value;
    document.getElementById('final_email').value     = document.getElementById('email').value;
    document.getElementById('final_password').value  = document.getElementById('password').value;
    const genderRadio = document.querySelector('input[name="gender"]:checked');
    if (genderRadio) {
        document.getElementById('final_gender').value = genderRadio.value;
    }
    document.getElementById('final_birthdate').value = document.getElementById('birthdate').value;
    const goalRadio = document.querySelector('input[name="user_goal"]:checked');
    if (goalRadio) {
        document.getElementById('final_user_goal').value = goalRadio.value;
    }
    document.getElementById('finalForm').submit();
}

function showStepDirect(step) {
    for (let i = 1; i <= totalSteps; i++) {
        const card = document.getElementById('step' + i);
        if (!card) continue;
        card.style.display   = i === step ? 'block' : 'none';
        card.style.opacity   = '1';
        card.style.transform = 'translateX(0)';
        card.classList.toggle('active', i === step);
    }
    currentStep = step;
    updateProgress();
}

document.addEventListener('DOMContentLoaded', function () {

    for (let i = 2; i <= totalSteps; i++) {
        const card = document.getElementById('step' + i);
        if (card) {
            card.style.display = 'none';
            card.classList.remove('active');
        }
    }

    document.querySelectorAll('.option-card:not(.category-card)').forEach(function (card) {
        card.addEventListener('click', function (e) {
            if (e.target.type === 'radio') return;
            const radio = this.querySelector('input[type="radio"]');
            if (!radio) return;
            document.querySelectorAll(`input[type="radio"][name="${radio.name}"]`).forEach(function (other) {
                other.checked = false;
                other.closest('.option-card').classList.remove('checked');
            });
            radio.checked = true;
            this.classList.add('checked');
        });
    });

    document.querySelectorAll('.category-card').forEach(function (card) {
        card.addEventListener('click', function (e) {
            e.preventDefault();
            const checkbox = this.querySelector('input[type="checkbox"]');
            if (!checkbox) return;
            checkbox.checked = !checkbox.checked;
            this.classList.toggle('checked', checkbox.checked);
        });
    });

    const toggleBtn = document.getElementById('togglePassword');
    if (toggleBtn) {
        toggleBtn.addEventListener('click', function () {
            const input = document.getElementById('password');
            if (!input) return;
            const hidden = input.type === 'password';
            input.type = hidden ? 'text' : 'password';
            this.innerHTML = hidden
                ? '<i class="fa-solid fa-eye-slash" style="color:#000"></i>'
                : '<i class="fa-solid fa-eye" style="color:#000"></i>';
        });
    }
});

window.nextStep        = nextStep;
window.prevStep        = prevStep;
window.submitFinalForm = submitFinalForm;
window.showStepDirect  = showStepDirect;
