<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link rel="icon" type="image/x-icon" href="{{ asset('images/logo.png') }}">
  <title>Stead-E | Create Account</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="{{ asset('storage/js/register.js') }}" defer></script>
  <script src="{{ asset('storage/js/app.js') }}" defer></script>
  <script src="https://kit.fontawesome.com/0bf932704c.js" crossorigin="anonymous"></script>
  <link rel="icon" type="image/x-icon" href="images/stead-e_logo.png">
  <link rel="stylesheet" href="{{ asset('storage/css/register.css') }}">
</head>
<body>

<div class="back-to-home-container">
  <a href="{{ url('/') }}" class="back-to-home">
    <span><i class="fa-solid fa-arrow-left"></i> <span class="back-text">Back to Home</span></span>
  </a>
</div>

<div class="progress-container">
  <div class="progress">
    <div class="progress-bar" id="progressBar"></div>
  </div>
  <div class="progress-steps">
    <span class="step active" data-step="1">1</span>
    <span class="step" data-step="2">2</span>
    <span class="step" data-step="3">3</span>
    <span class="step" data-step="4">4</span>
    <span class="step" data-step="5">5</span>
    <span class="step" data-step="6">6</span>
  </div>
</div>

<div class="registration-container">
<div class="container-fluid">
<div class="row justify-content-center">
<div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5">

@if ($errors->any())
  <div class="alert alert-danger">
    <ul class="mb-0">
      @foreach ($errors->all() as $error)
        <li>{{ $error }}</li>
      @endforeach
    </ul>
  </div>
@endif

<!-- Step 1 -->
<div class="registration-card active" id="step1">
  <div class="text-center mb-2">
    <img src="{{ asset('images/stead-e_logo.png') }}" class="registration-logo">
  </div>

  <h1 class="registration-title">Create Account</h1>
  <p class="registration-subtitle">Start creating habits today</p>

  <form>
    <div class="form-group">
      <label class="form-label">Name</label>
      <input type="text" id="name" class="form-control @error('name') is-invalid @enderror" 
             value="{{ old('name') }}" required>
      @error('name')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-group">
      <label class="form-label">Username</label>
      <input type="text" id="username" class="form-control @error('username') is-invalid @enderror" 
             value="{{ old('username') }}" required>
      @error('username')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-group">
      <label class="form-label">Email</label>
      <input type="email" id="email" class="form-control @error('email') is-invalid @enderror" 
             value="{{ old('email') }}" required>
      @error('email')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-group">
      <label class="form-label">Password</label>
      <input type="password" id="password" class="form-control @error('password') is-invalid @enderror" required>
      @error('password')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>
    <div class="form-buttons">
      <button type="button" class="btn btn-primary" onclick="nextStep()">Continue</button>
    </div>
    
  </form>

  <div class="login-redirect mt-2">
    <p>Already have an account? <a href="{{ route('login') }}">sign in</a></p>
  </div>
</div>

<!-- Step 2 -->
<div class="registration-card" id="step2">
  <div class="text-center mb-2">
    <img src="{{ asset('images/stead-e_logo.png') }}" class="registration-logo">
  </div>

  <h1 class="registration-title">About You</h1>
  <p class="registration-subtitle">Tell us a bit more</p>

  <form>
    <div class="form-group">
      <label class="form-label">Gender</label>
      <div class="options-grid">
        <label class="option-card">
          <input type="radio" name="gender" value="male" {{ old('gender') == 'male' ? 'checked' : '' }} required> 
          <span class="option-icon"><i class="fa-solid fa-mars" style="color: rgb(0, 0, 0);"></i></span>
          <span>Male</span>
        </label>
        <label class="option-card">
          <input type="radio" name="gender" value="female" {{ old('gender') == 'female' ? 'checked' : '' }} required>
          <span class="option-icon"><i class="fa-solid fa-venus" style="color: rgb(0, 0, 0);"></i></span>
          <span>Female</span>
        </label>
        <label class="option-card">
          <input type="radio" name="gender" value="other" {{ old('gender') == 'other' ? 'checked' : '' }} required>
          <span class="option-icon"><i class="fa-solid fa-genderless" style="color: rgb(0, 0, 0);"></i></span>
          <span>Other</span>
        </label>
      </div>
      @error('gender')
        <div class="text-danger mt-2">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-group">
      <label class="form-label">Date of Birth</label>
      <input type="date" id="birthdate" class="form-control @error('birthdate') is-invalid @enderror" 
             value="{{ old('birthdate') }}" required>
      @error('birthdate')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-buttons">
      <button type="button" class="btn btn-secondary" onclick="prevStep()">Back</button>
      <button type="button" class="btn btn-primary" onclick="nextStep()">Continue</button>
    </div>
  </form>
</div>

<!-- Step 3 -->
<div class="registration-card" id="step3">
  <div class="text-center mb-2">
    <img src="{{ asset('images/stead-e_logo.png') }}" class="registration-logo">
  </div>

  <h1 class="registration-title">Your Weight</h1>
  <p class="registration-subtitle">How much do you weigh?</p>

  <form>
    <div class="form-group">
      <label class="form-label">Weight (kg)</label>
      <input type="number" id="weight" class="form-control @error('weight') is-invalid @enderror" 
             min="30" max="300" step="0.1" value="{{ old('weight') }}" required>
      @error('weight')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-buttons">
      <button type="button" class="btn btn-secondary" onclick="prevStep()">Back</button>
      <button type="button" class="btn btn-primary" onclick="nextStep()">Continue</button>
    </div>
  </form>
</div>

<!-- Step 4 -->
<div class="registration-card" id="step4">
  <div class="text-center mb-2">
    <img src="{{ asset('images/stead-e_logo.png') }}" class="registration-logo">
  </div>

  <h1 class="registration-title">Your Height</h1>
  <p class="registration-subtitle">How tall are you?</p>

  <form>
    <div class="form-group">
      <label class="form-label">Height (cm)</label>
      <input type="number" id="height" class="form-control @error('height') is-invalid @enderror" 
             min="100" max="250" value="{{ old('height') }}" required>
      @error('height')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-buttons">
      <button type="button" class="btn btn-secondary" onclick="prevStep()">Back</button>
      <button type="button" class="btn btn-primary" onclick="nextStep()">Continue</button>
    </div>
  </form>
</div>

<!-- Step 5 -->
<div class="registration-card" id="step5">
  <div class="text-center mb-2">
    <img src="{{ asset('images/stead-e_logo.png') }}" class="registration-logo">
  </div>

  <h1 class="registration-title">Sleep Schedule</h1>
  <p class="registration-subtitle">When do you sleep and wake?</p>

  <form>
    <div class="form-group">
      <label class="form-label">Sleep Time</label>
      <input type="time" id="sleep_time" class="form-control @error('sleep_time') is-invalid @enderror" 
             value="{{ old('sleep_time') }}" required>
      @error('sleep_time')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-group">
      <label class="form-label">Wake Time</label>
      <input type="time" id="wake_time" class="form-control @error('wake_time') is-invalid @enderror" 
             value="{{ old('wake_time') }}" required>
      @error('wake_time')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-buttons">
      <button type="button" class="btn btn-secondary" onclick="prevStep()">Back</button>
      <button type="button" class="btn btn-primary" onclick="nextStep()">Continue</button>
    </div>
  </form>
</div>

<!-- Step 6 -->
<div class="registration-card" id="step6">
  <div class="text-center mb-2">
    <img src="{{ asset('images/stead-e_logo.png') }}" class="registration-logo">
  </div>

  <h1 class="registration-title">Your Goal</h1>
  <p class="registration-subtitle">What do you want to achieve?</p>

  <form action="{{ route('register') }}" method="POST" id="finalForm">
    @csrf

    <input type="hidden" name="name" id="final_name">
    <input type="hidden" name="username" id="final_username">
    <input type="hidden" name="email" id="final_email">
    <input type="hidden" name="password" id="final_password">
    <input type="hidden" name="gender" id="final_gender">
    <input type="hidden" name="birthdate" id="final_birthdate">
    <input type="hidden" name="weight" id="final_weight">
    <input type="hidden" name="height" id="final_height">
    <input type="hidden" name="sleep_time" id="final_sleep_time">
    <input type="hidden" name="wake_time" id="final_wake_time">
    <input type="hidden" name="user_goal" id="final_user_goal">

    <div class="form-group">
      <label class="form-label">My main goal is</label>
      <div class="options-grid">
        <label class="option-card">
          <input type="radio" name="user_goal" value="weight_loss" {{ old('user_goal') == 'weight_loss' ? 'checked' : '' }} required> 
          <span>Weight Loss</span>
        </label>
        <label class="option-card">
          <input type="radio" name="user_goal" value="consistency" {{ old('user_goal') == 'consistency' ? 'checked' : '' }} required> 
          <span>Consistency</span>
        </label>
        <label class="option-card">
          <input type="radio" name="user_goal" value="quit_habit" {{ old('user_goal') == 'quit_habit' ? 'checked' : '' }} required> 
          <span>Quit Habit</span>
        </label>
        <label class="option-card">
          <input type="radio" name="user_goal" value="explore" {{ old('user_goal') == 'explore' ? 'checked' : '' }} required> 
          <span>Exploring</span>
        </label>
      </div>
      @error('user_goal')
        <div class="text-danger mt-2">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-buttons">
      <button type="button" class="btn btn-secondary" onclick="prevStep()">Back</button>
      <button type="button" class="btn btn-primary" onclick="submitFinalForm()">Complete Registration</button>
    </div>
  </form>
</div>

</div>
</div>
</div>
</div>

<script>
function submitFinalForm() {
    document.getElementById('final_name').value = document.getElementById('name').value;
    document.getElementById('final_username').value = document.getElementById('username').value;
    document.getElementById('final_email').value = document.getElementById('email').value;
    document.getElementById('final_password').value = document.getElementById('password').value;
    
    const genderRadio = document.querySelector('input[name="gender"]:checked');
    if (genderRadio) {
        document.getElementById('final_gender').value = genderRadio.value;
    }
    
    document.getElementById('final_birthdate').value = document.getElementById('birthdate').value;
    document.getElementById('final_weight').value = document.getElementById('weight').value;
    document.getElementById('final_height').value = document.getElementById('height').value;
    document.getElementById('final_sleep_time').value = document.getElementById('sleep_time').value;
    document.getElementById('final_wake_time').value = document.getElementById('wake_time').value;
    
    const goalRadio = document.querySelector('input[name="user_goal"]:checked');
    if (goalRadio) {
        document.getElementById('final_user_goal').value = goalRadio.value;
    }
    
    document.getElementById('finalForm').submit();
}

// Errors
@if ($errors->any())
document.addEventListener('DOMContentLoaded', function() {
    @if ($errors->has('name') || $errors->has('username') || $errors->has('email') || $errors->has('password'))
        showStep(1);
    @elseif ($errors->has('gender') || $errors->has('birthdate'))
        showStep(2);
    @elseif ($errors->has('weight'))
        showStep(3);
    @elseif ($errors->has('height'))
        showStep(4);
    @elseif ($errors->has('sleep_time') || $errors->has('wake_time'))
        showStep(5);
    @elseif ($errors->has('user_goal'))
        showStep(6);
    @endif
});
@endif
</script>

</body>
</html>