// Login
document.addEventListener('DOMContentLoaded', function() {
    initializeLoginForm();
    initializePasswordToggle();
    initializeSocialButtons();
});

function initializeLoginForm() {
    const loginForm = document.getElementById('loginForm');
    
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            if (validateLoginForm()) {
                const submitBtn = this.querySelector('button[type="submit"]');
                const originalText = submitBtn.textContent;
                submitBtn.textContent = 'Signing In...';
                submitBtn.disabled = true;
            }
        });
    }
}

function validateLoginForm() {
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    let isValid = true;
    
    // Reset previous errors
    resetValidationStyles();
    
    // Validate email
    if (!email.value.trim()) {
        showFieldError(email, 'Email is required');
        isValid = false;
    }
    
    // Validate password
    if (!password.value) {
        showFieldError(password, 'Password is required');
        isValid = false;
    } else if (password.value.length < 6) {
        showFieldError(password, 'Password must be at least 6 characters');
        isValid = false;
    }
    
    return isValid;
}

function showFieldError(field, message) {
    field.style.borderColor = '#dc3545';
    field.style.boxShadow = '0 0 0 3px rgba(220, 53, 69, 0.1)';

    const existingError = field.parentNode.querySelector('.field-error');
    if (existingError) {
        existingError.remove();
    }
    
    // Error message
    const errorDiv = document.createElement('div');
    errorDiv.className = 'field-error';
    errorDiv.style.color = '#dc3545';
    errorDiv.style.fontSize = '0.8rem';
    errorDiv.style.marginTop = '0.5rem';
    errorDiv.textContent = message;
    
    field.parentNode.appendChild(errorDiv);
    
    // Animation
    field.classList.add('shake');
    setTimeout(() => {
        field.classList.remove('shake');
    }, 500);
}

function resetValidationStyles() {
    const fields = document.querySelectorAll('.form-control');
    const errorMessages = document.querySelectorAll('.field-error');
    
    fields.forEach(field => {
        field.style.borderColor = '';
        field.style.boxShadow = '';
    });
    
    errorMessages.forEach(error => error.remove());
}

function initializePasswordToggle() {
    const toggleBtn = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');
    
    if (toggleBtn && passwordInput) {
        toggleBtn.addEventListener('click', function() {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
        
            const eyeIcon = this.querySelector('.eye-icon');
            if (type === 'text') {
                eyeIcon.textContent = '🙈';
            } else {
                eyeIcon.textContent = '👁️';
            }
            
            this.style.transform = 'scale(1.2)';
            setTimeout(() => {
                this.style.transform = '';
            }, 200);
        });
    }
}

function initializeSocialButtons() {
    const googleBtn = document.querySelector('.btn-google');
    const facebookBtn = document.querySelector('.btn-facebook');
    
    if (googleBtn) {
        googleBtn.addEventListener('click', function() {
            // Simulate Google login
            showLoadingState(this, 'Connecting to Google...');
            setTimeout(() => {
                alert('Google login would be implemented here');
                resetButtonState(this, 'Continue with Google');
            }, 1500);
        });
    }
    
    if (facebookBtn) {
        facebookBtn.addEventListener('click', function() {
            // Simulate Facebook login
            showLoadingState(this, 'Connecting to Facebook...');
            setTimeout(() => {
                alert('Facebook login would be implemented here');
                resetButtonState(this, 'Continue with Facebook');
            }, 1500);
        });
    }
}

function showLoadingState(button, loadingText) {
    button.disabled = true;
    button.style.opacity = '0.7';
    const originalText = button.textContent;
    button.setAttribute('data-original-text', originalText);
    button.innerHTML = loadingText;
}

function resetButtonState(button, originalText) {
    button.disabled = false;
    button.style.opacity = '1';
    button.innerHTML = originalText;
}

// Enter key submission
document.addEventListener('keydown', function(e) {
    if (e.key === 'Enter') {
        const loginForm = document.getElementById('loginForm');
        if (loginForm && document.activeElement.closest('#loginForm')) {
            loginForm.dispatchEvent(new Event('submit'));
        }
    }
});

// Auto-focus on email field
window.addEventListener('load', function() {
    const emailField = document.getElementById('email');
    if (emailField) {
        emailField.focus();
    }
});
