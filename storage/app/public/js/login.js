document.addEventListener('DOMContentLoaded', function () {
    initLoginForm();
    initPasswordToggle();
});

// login form
function initLoginForm() {
    const loginForm = document.getElementById('loginForm');
    if (!loginForm) return;

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        if (!validateLoginForm()) return;

        // Show loading state on the button, then submit the form
        const submitBtn = this.querySelector('button[type="submit"]');
        submitBtn.textContent = 'Signing In...';
        submitBtn.disabled = true;
        this.submit();
    });
}

// form validation
function validateLoginForm() {
    const email    = document.getElementById('email');
    const password = document.getElementById('password');
    let isValid    = true;

    resetValidationStyles();

    if (!email.value.trim()) {
        showFieldError(email, 'Email is required');
        isValid = false;
    }

    if (!password.value) {
        showFieldError(password, 'Password is required');
        isValid = false;
    } else if (password.value.length < 6) {
        showFieldError(password, 'Password must be at least 6 characters');
        isValid = false;
    }

    return isValid;
}

// field error
function showFieldError(field, message) {
    field.style.borderColor = '#dc3545';
    field.style.boxShadow   = '0 0 0 3px rgba(220, 53, 69, 0.1)';

    const existing = field.parentNode.querySelector('.field-error');
    if (existing) existing.remove();

    const errorDiv       = document.createElement('div');
    errorDiv.className   = 'field-error';
    errorDiv.style.color = '#dc3545';
    errorDiv.style.fontSize  = '0.8rem';
    errorDiv.style.marginTop = '0.5rem';
    errorDiv.textContent = message;

    field.parentNode.appendChild(errorDiv);
}

// clears error style
function resetValidationStyles() {
    document.querySelectorAll('.form-control').forEach(field => {
        field.style.borderColor = '';
        field.style.boxShadow   = '';
    });
    document.querySelectorAll('.field-error').forEach(el => el.remove());
}

// password toggle
function initPasswordToggle() {
    const toggleBtn    = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');
    if (!toggleBtn || !passwordInput) return;

    toggleBtn.addEventListener('click', function () {
        const isHidden = passwordInput.getAttribute('type') === 'password';
        passwordInput.setAttribute('type', isHidden ? 'text' : 'password');

        const eyeIcon = this.querySelector('.eye-icon');
        if (eyeIcon) eyeIcon.textContent = isHidden ? '🙈' : '👁️';

        this.style.transform = 'scale(1.2)';
        setTimeout(() => { this.style.transform = ''; }, 200);
    });
}

// auto focus email field on load
window.addEventListener('load', function () {
    const emailField = document.getElementById('email');
    if (emailField) emailField.focus();
});
