@extends('layouts.app')

@section('title', 'Home')
@vite(['resources/css/index.css'])

@section('content')
    <main class="container main-content">
    <div class="row align-items-center">
      <div class="col-lg-6 main-text">
        <h1 class="gradient-text">
          Shape your<br />
          Future.<br />
          Create new<br />
          habits.
        </h1>
        <p class="lead">
          Your all-in-one fitness partner. Track hydration, exercise, <br/>
          and health metrics together.
        </p>
        <a href="{{ url('/register') }}" class="btn btn-primary btn-start">Start for Free</a>
      </div>
      <div class="col-lg-6">
        <div class="slideshow-container">
          <div class="slide active">
            <div class="slide-text">Create Good Habits</div>
            <p class="lead">
            Change your life by slowly adding new healthy habits and sticking to them.
            </p>
            <img src="images/slide1.png" alt="Good Habits" class="slide-image">
          </div>
          <div class="slide">
            <div class="slide-text">Stay Together and Strong</div>
            <p class="lead">
            Find friends to discuss common topics. Complete challenges together.
            </p>
            <img src="images/slide2.png" alt="Stay Together" class="slide-image">
          </div>
          <div class="slide">
            <div class="slide-text">Track your progress</div>
            <p class="lead">
            Everyday you become one step closer to your goals.
            </p>
            <img src="images/slide3.png" alt="Eredmények" class="slide-image">
          </div>
          <div class="slide-indicators">
            <span class="indicator active" data-index="0"></span>
            <span class="indicator" data-index="1"></span>
            <span class="indicator" data-index="2"></span>
          </div>
        </div>
      </div>
    </div>
  </main>

  <section id="functions"  class="functions-section py-5">
    <div class="container">
      <h2 class="text-center mb-5 text-white">Functions</h2>
      <div class="row g-4">
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon">🏃‍♂️</div>
            <h5 class="card-title">Habit Creation</h5>
            <p class="card-text">Easily create and customize your daily habits with reminders and goals.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon">📊</div>
            <h5 class="card-title">Progress Tracking</h5>
            <p class="card-text">Monitor your streaks, achievements, and overall progress with detailed stats.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon">👥</div>
            <h5 class="card-title">Community Challenges</h5>
            <p class="card-text">Join groups and challenges to stay motivated with friends and like-minded people.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon">💧</div>
            <h5 class="card-title">Hydration Tracker</h5>
            <p class="card-text">Track your daily water intake and set personalized hydration goals.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon">🏋️‍♀️</div>
            <h5 class="card-title">Workout Logging</h5>
            <p class="card-text">Log your exercises, sets, and reps to build a comprehensive fitness history.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon">📅</div>
            <h5 class="card-title">Calendar Integration</h5>
            <p class="card-text">Sync with your calendar for reminders and habit scheduling.</p>
          </div>
        </div>
      </div>
    </div>
  </section>

  <section id="download" class="download-section py-5">
    <div class="container">
      <h2 class="text-center mb-5 download-section-title">Download</h2>
      <div class="download-card">
        <div class="row align-items-center h-100">
          <div class="col-lg-6 download-text-content">
            <h3 class="download-title">Get Stead-E Now</h3>
            <p class="download-text">
              Start your journey to better habits today. Download Stead-E for free and join thousands of users who are already shaping their future.
            </p>
            <p class="download-text">
              Available now on the Google Play Store with more platforms coming soon.
            </p>
            <div class="mt-4">
              <a href="#" class="google-play-btn">
                <img src="images/google_play_badge.webp" alt="Get it on Google Play" class="play-store-badge">
              </a>
            </div>
          </div>
          <div class="col-lg-6 download-image-col">
            <div class="download-image-wrapper">
              <img src="images/hand_img.png" alt="Hand holding phone" class="download-image">
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <section id="reviews" class="reviews-section py-5">
    <div class="container">
      <h2 class="text-center mb-5 reviews-section-title">What Our Users Say</h2>
      <div class="row g-4">
        <div class="col-lg-4 col-md-6">
          <div class="review-card">
            <div class="review-header">
              <div class="reviewer-info">
                <div class="reviewer-avatar">JD</div>
                <div class="reviewer-details">
                  <h5 class="reviewer-name">John Doe</h5>
                  <div class="review-rating">
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="review-content">
              <p class="review-text">"This app completely changed my daily routine. I've been able to build consistent habits that I've struggled with for years!"</p>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="review-card">
            <div class="review-header">
              <div class="reviewer-info">
                <div class="reviewer-avatar">SJ</div>
                <div class="reviewer-details">
                  <h5 class="reviewer-name">Sarah Johnson</h5>
                  <div class="review-rating">
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="review-content">
              <p class="review-text">"The community challenges keep me motivated. I've made new friends who share my fitness goals!"</p>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="review-card">
            <div class="review-header">
              <div class="reviewer-info">
                <div class="reviewer-avatar">MR</div>
                <div class="reviewer-details">
                  <h5 class="reviewer-name">Mike Rodriguez</h5>
                  <div class="review-rating">
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="review-content">
              <p class="review-text">"Tracking my hydration and workouts has never been easier. The progress charts are incredibly motivating!"</p>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="review-card">
            <div class="review-header">
              <div class="reviewer-info">
                <div class="reviewer-avatar">ET</div>
                <div class="reviewer-details">
                  <h5 class="reviewer-name">Emma Thompson</h5>
                  <div class="review-rating">
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="review-content">
              <p class="review-text">"I love how simple it is to create and track habits. The reminders are perfect and not annoying!"</p>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="review-card">
            <div class="review-header">
              <div class="reviewer-info">
                <div class="reviewer-avatar">AK</div>
                <div class="reviewer-details">
                  <h5 class="reviewer-name">Alex Kim</h5>
                  <div class="review-rating">
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="review-content">
              <p class="review-text">"The calendar integration is a game-changer. It helps me plan my week and stick to my fitness goals."</p>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="review-card">
            <div class="review-header">
              <div class="reviewer-info">
                <div class="reviewer-avatar">LW</div>
                <div class="reviewer-details">
                  <h5 class="reviewer-name">Lisa Wang</h5>
                  <div class="review-rating">
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="review-content">
              <p class="review-text">"After just one month, I've already seen significant improvements in my health and energy levels!"</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
@endsection