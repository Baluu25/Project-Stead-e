<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link rel="icon" type="image/x-icon" href="{{ asset('images/logo.png') }}">
  <title>Stead-E | Create Account</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="{{ asset('storage/js/app.js') }}" defer></script>
  <script src="{{ asset('storage/js/register.js') }}" defer></script>
  <script src="https://kit.fontawesome.com/0bf932704c.js" crossorigin="anonymous"></script>
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

<!-- Step 1: Account Info -->
<div class="registration-card active" id="step1">
  <div class="text-center mb-2">
    <img src="{{ asset('images/stead-e_logo.png') }}" class="registration-logo">
  </div>

  <h1 class="registration-title">Create Account</h1>
  <p class="registration-subtitle">Start building better habits today</p>

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
      <div class="input-group">
        <input type="password" id="password" class="form-control @error('password') is-invalid @enderror" required>
        <button type="button" id="togglePassword" class="btn btn-outline-secondary">
          <i class="fa-solid fa-eye" style="color:#000"></i>
        </button>
      </div>
      @error('password')
        <div class="invalid-feedback d-block">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-group">
      <label class="form-label">Gender</label>
      <select id="gender" class="form-control @error('gender') is-invalid @enderror">
        <option value="" disabled {{ old('gender') ? '' : 'selected' }}>Select gender</option>
        <option value="male"   {{ old('gender') == 'male'   ? 'selected' : '' }}>Male</option>
        <option value="female" {{ old('gender') == 'female' ? 'selected' : '' }}>Female</option>
        <option value="other"  {{ old('gender') == 'other'  ? 'selected' : '' }}>Other</option>
      </select>
      @error('gender')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-group">
      <label class="form-label">Birthday</label>
      <input type="date" id="birthdate" class="form-control @error('birthdate') is-invalid @enderror"
             value="{{ old('birthdate') }}">
      @error('birthdate')
        <div class="invalid-feedback">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-buttons">
      <button type="button" class="btn btn-primary" onclick="nextStep()">Continue</button>
    </div>
  </form>

  <div class="login-redirect mt-2">
    <p>Already have an account? <a href="{{ route('login') }}">Sign in</a></p>
  </div>
</div>

<!-- Step 2: Goal -->
<div class="registration-card" id="step2">
  <div class="text-center mb-2">
    <img src="{{ asset('images/stead-e_logo.png') }}" class="registration-logo">
  </div>

  <h1 class="registration-title">Your Goal</h1>
  <p class="registration-subtitle">What are you working towards?</p>

  <form>
    <div class="form-group">
      <div class="options-grid">
        <label class="option-card {{ old('user_goal') == 'weight_loss' ? 'checked' : '' }}">
          <input type="radio" name="user_goal" value="weight_loss"
                 {{ old('user_goal') == 'weight_loss' ? 'checked' : '' }} required>
          <span class="option-icon"><i class="fa-solid fa-scale-balanced"></i></span>
          <span>Weight Loss</span>
        </label>
        <label class="option-card {{ old('user_goal') == 'consistency' ? 'checked' : '' }}">
          <input type="radio" name="user_goal" value="consistency"
                 {{ old('user_goal') == 'consistency' ? 'checked' : '' }} required>
          <span class="option-icon"><i class="fa-solid fa-calendar-check"></i></span>
          <span>Consistency</span>
        </label>
        <label class="option-card {{ old('user_goal') == 'quit_habit' ? 'checked' : '' }}">
          <input type="radio" name="user_goal" value="quit_habit"
                 {{ old('user_goal') == 'quit_habit' ? 'checked' : '' }} required>
          <span class="option-icon"><i class="fa-solid fa-ban"></i></span>
          <span>Quit a Habit</span>
        </label>
        <label class="option-card {{ old('user_goal') == 'explore' ? 'checked' : '' }}">
          <input type="radio" name="user_goal" value="explore"
                 {{ old('user_goal') == 'explore' ? 'checked' : '' }} required>
          <span class="option-icon"><i class="fa-solid fa-compass"></i></span>
          <span>Exploring</span>
        </label>
      </div>
      @error('user_goal')
        <div class="text-danger mt-2">{{ $message }}</div>
      @enderror
    </div>

    <div class="form-buttons">
      <button type="button" class="btn btn-secondary" onclick="prevStep()">Back</button>
      <button type="button" class="btn btn-primary" onclick="nextStep()">Continue</button>
    </div>
  </form>
</div>

<!-- Step 3: Focus Areas + Submit -->
<div class="registration-card" id="step3">
  <div class="text-center mb-2">
    <img src="{{ asset('images/stead-e_logo.png') }}" class="registration-logo">
  </div>

  <h1 class="registration-title">Focus Areas</h1>
  <p class="registration-subtitle">Pick what you want to build habits around</p>

  <form action="{{ route('register') }}" method="POST" id="finalForm">
    @csrf

    {{-- Carry forward step 1 values --}}
    <input type="hidden" name="name"      id="final_name">
    <input type="hidden" name="username"  id="final_username">
    <input type="hidden" name="email"     id="final_email">
    <input type="hidden" name="password"  id="final_password">
    <input type="hidden" name="gender"    id="final_gender">
    <input type="hidden" name="birthdate" id="final_birthdate">
    {{-- Carry forward step 2 value --}}
    <input type="hidden" name="user_goal" id="final_user_goal">

    <div class="form-group">
      @php $oldCats = old('preferred_categories', []); @endphp
      <div class="options-grid">

        <label class="option-card category-card {{ in_array('Fitness', $oldCats) ? 'checked' : '' }}">
          <input type="checkbox" name="preferred_categories[]" value="Fitness"
                 {{ in_array('Fitness', $oldCats) ? 'checked' : '' }}>
          <span class="option-icon"><i class="fa-solid fa-dumbbell"></i></span>
          <span>Fitness</span>
        </label>

        <label class="option-card category-card {{ in_array('Nutrition', $oldCats) ? 'checked' : '' }}">
          <input type="checkbox" name="preferred_categories[]" value="Nutrition"
                 {{ in_array('Nutrition', $oldCats) ? 'checked' : '' }}>
          <span class="option-icon"><i class="fa-solid fa-apple-whole"></i></span>
          <span>Nutrition</span>
        </label>

        <label class="option-card category-card {{ in_array('Mindfulness', $oldCats) ? 'checked' : '' }}">
          <input type="checkbox" name="preferred_categories[]" value="Mindfulness"
                 {{ in_array('Mindfulness', $oldCats) ? 'checked' : '' }}>
          <span class="option-icon"><i class="fa-solid fa-brain"></i></span>
          <span>Mindfulness</span>
        </label>

        <label class="option-card category-card {{ in_array('Study', $oldCats) ? 'checked' : '' }}">
          <input type="checkbox" name="preferred_categories[]" value="Study"
                 {{ in_array('Study', $oldCats) ? 'checked' : '' }}>
          <span class="option-icon"><i class="fa-solid fa-book"></i></span>
          <span>Study</span>
        </label>

        <label class="option-card category-card {{ in_array('Work', $oldCats) ? 'checked' : '' }}">
          <input type="checkbox" name="preferred_categories[]" value="Work"
                 {{ in_array('Work', $oldCats) ? 'checked' : '' }}>
          <span class="option-icon"><i class="fa-solid fa-briefcase"></i></span>
          <span>Work</span>
        </label>

      </div>
      @error('preferred_categories')
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

@if ($errors->any())
<script>
document.addEventListener('DOMContentLoaded', function () {
    @if ($errors->has('name') || $errors->has('username') || $errors->has('email') || $errors->has('password') || $errors->has('gender') || $errors->has('birthdate'))
        showStepDirect(1);
    @elseif ($errors->has('user_goal'))
        showStepDirect(2);
    @elseif ($errors->has('preferred_categories'))
        showStepDirect(3);
    @endif
});
</script>
@endif


</body>
</html>
