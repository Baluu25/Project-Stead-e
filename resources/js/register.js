let currentStep = 1;
const totalSteps = 6;

function nextStep() {
    if (!validateStep(currentStep)) {
        return;
    }
    
    const currentCard = document.getElementById('step' + currentStep);
    currentCard.style.opacity = '0';
    currentCard.style.transform = 'translateX(-100%)';
    
    setTimeout(() => {
        currentCard.classList.remove('active');
        currentCard.style.display = 'none';
        
        currentStep++;
        const nextCard = document.getElementById('step' + currentStep);
        if (nextCard) {
            nextCard.style.display = 'block';
            nextCard.classList.add('active');
            setTimeout(() => {
                nextCard.style.opacity = '1';
                nextCard.style.transform = 'translateX(0)';
            }, 10);
        }
        
        updateProgress();
    }, 300);
}

function prevStep() {
    if (currentStep <= 1) return;
    
    const currentCard = document.getElementById('step' + currentStep);
    currentCard.style.opacity = '0';
    currentCard.style.transform = 'translateX(100%)';
    
    setTimeout(() => {
        currentCard.classList.remove('active');
        currentCard.style.display = 'none';
        
        currentStep--;
        const prevCard = document.getElementById('step' + currentStep);
        if (prevCard) {
            prevCard.style.display = 'block';
            prevCard.classList.add('active');
            setTimeout(() => {
                prevCard.style.opacity = '1';
                prevCard.style.transform = 'translateX(0)';
            }, 10);
        }
        
        updateProgress();
    }, 300);
}

function validateStep(step) {
    if (step === 1) {
        const name = document.getElementById('name').value.trim();
        const username = document.getElementById('username').value.trim();
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;
        
        if (name === '') {
            alert('Please enter your name');
            return false;
        }
        if (username === '') {
            alert('Please enter username');
            return false;
        }
        if (email === '' || !email.includes('@')) {
            alert('Please enter valid email');
            return false;
        }
        if (password.length < 6) {
            alert('Password must be at least 6 characters');
            return false;
        }
        return true;
    }
    
    if (step === 2) {
        const genderSelected = document.querySelector('input[name="gender"]:checked');
        if (!genderSelected) {
            alert('Please select gender');
            return false;
        }
        return true;
    }
    
    if (step === 3) {
        const weight = document.getElementById('weight').value;
        if (weight === '' || weight < 30 || weight > 300) {
            alert('Please enter valid weight (30-300 kg)');
            return false;
        }
        return true;
    }
    
    if (step === 4) {
        const height = document.getElementById('height').value;
        if (height === '' || height < 100 || height > 250) {
            alert('Please enter valid height (100-250 cm)');
            return false;
        }
        return true;
    }
    
    if (step === 5) {
        const goalSelected = document.querySelector('input[name="user_goal"]:checked');
        if (!goalSelected) {
            alert('Please select goal');
            return false;
        }
        return true;
    }
    
    if (step === 6) {
        const activitySelected = document.querySelector('input[name="activity_level"]:checked');
        if (!activitySelected) {
            alert('Please select activity level');
            return false;
        }
        return true;
    }
    
    return true;
}

function updateProgress() {
    const progressBar = document.getElementById('progressBar');
    if (progressBar) {
        const progressPercent = ((currentStep - 1) / (totalSteps - 1)) * 100;
        progressBar.style.width = progressPercent + '%';
    }
    
    const steps = document.querySelectorAll('.step');
    steps.forEach((step, index) => {
        if (index + 1 <= currentStep) {
            step.classList.add('active');
        } else {
            step.classList.remove('active');
        }
    });
}

document.addEventListener('DOMContentLoaded', function() {
    for (let i = 2; i <= totalSteps; i++) {
        const card = document.getElementById('step' + i);
        if (card) {
            card.style.display = 'none';
            card.classList.remove('active');
        }
    }
    
    const optionCards = document.querySelectorAll('.option-card');
    optionCards.forEach(card => {
        card.addEventListener('click', function() {
            const radioInput = this.querySelector('input[type="radio"]');
            if (radioInput) {
                const groupName = radioInput.name;
                document.querySelectorAll(`input[name="${groupName}"]`).forEach(radio => {
                    if (radio !== radioInput) {
                        radio.checked = false;
                        radio.closest('.option-card').classList.remove('checked');
                    }
                });
                
                radioInput.checked = true;
                this.classList.add('checked');
            }
        });
    });
    
    const toggleBtn = document.getElementById('togglePassword');
    if (toggleBtn) {
        toggleBtn.addEventListener('click', function() {
            const passwordInput = document.getElementById('password');
            if (passwordInput) {
                if (passwordInput.type === 'password') {
                    passwordInput.type = 'text';
                    this.innerHTML = '🙈';
                } else {
                    passwordInput.type = 'password';
                    this.innerHTML = '👁️';
                }
            }
        });
    }
});

window.nextStep = nextStep;
window.prevStep = prevStep;