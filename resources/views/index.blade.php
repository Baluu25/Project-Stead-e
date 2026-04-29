@extends('layouts.app')

@section('title', 'Home')
@section('styles')
  <link rel="stylesheet" href="{{ asset('storage/css/index.css') }}">
  <style>
    .reviewer-avatar img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      object-fit: cover;
      display: block;
    }
    .reviewer-avatar img[src=""] {
      display: none;
    }
  </style>
@endsection

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
        <a href="{{ url('/register') }}" class="btn btn-primary">Start for Free</a>
      </div>
      <div class="col-lg-6">
        <div class="slideshow-container">
          <div class="slide active">
            <div class="slide-text">Build Lasting Habits</div>
            <p class="lead">
            Small daily actions shape who you are. Build routines that stick with you.
            </p>
            <img src="images/slide1.png" alt="Good Habits" class="slide-image">
          </div>
          <div class="slide">
            <div class="slide-text">Set and Reach Your Goals</div>
            <p class="lead">
            Define what matters most to you. Break big goals into simple daily steps.
            </p>
            <img src="images/slide2.png" alt="Stay Together" class="slide-image">
          </div>
          <div class="slide">
            <div class="slide-text">See Your Progress</div>
            <p class="lead">
            View detailed stats and charts that show how far you have come each day.
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
            <div class="card-icon"><i class="fa-solid fa-person-running"></i></div>
            <h5 class="card-title">Habit Creation</h5>
            <p class="card-text">Easily create and customize your daily habits with categories and icons</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon"><i class="fa-solid fa-fire"></i></div>
            <h5 class="card-title">Progress Tracking</h5>
            <p class="card-text">Monitor your streaks and daily completion rates with detailed progress stats.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon"><i class="fa-solid fa-bullseye"></i></div>
            <h5 class="card-title">Goal Management</h5>
            <p class="card-text">Define personal goals with custom targets and deadlines, and track your progress.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon"><i class="fa-solid fa-chart-line"></i></div>
            <h5 class="card-title">Statistics & Analytics</h5>
            <p class="card-text">View your habit stats with weekly breakdowns and category analytics.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon"><i class="fa-solid fa-trophy"></i></div>
            <h5 class="card-title">Achievements</h5>
            <p class="card-text">Unlock achievement badges as you hit milestones and build consistent habits.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="function-card">
            <div class="card-icon"><i class="fa-solid fa-circle-user"></i></div>
            <h5 class="card-title">Profile Management</h5>
            <p class="card-text">Keep your profile up to date and manage personal settings.</p>
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
            <h3 class="download-title">Coming Soon</h3>
            <p class="download-text">
              We're working hard to bring Stead-E to your device. Stay tuned — the app will be available for download very soon!
            </p>
            <p class="download-text">
              The mobile app is currently under development. We'll be launching on the Google Play Store and more platforms soon.
            </p>
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
                <div class="reviewer-avatar">
<<<<<<< HEAD
                  <img src="images/reviews/arthur-radish.png" alt="Arthur Radish">
=======
                  <img src="" alt="John Doe">
>>>>>>> 4c1d2a96c53e1d46d7887d7cbb547c21e28093e9
                </div>
                <div class="reviewer-details">
                  <h5 class="reviewer-name">Arthur Radish</h5>
                  <div class="review-rating">
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star"></span>
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
                <div class="reviewer-avatar">
<<<<<<< HEAD
                  <img src="images/reviews/andrew-bartell.png" alt="Andrew Bartell">
=======
                  <img src="" alt="Sarah Johnson">
>>>>>>> 4c1d2a96c53e1d46d7887d7cbb547c21e28093e9
                </div>
                <div class="reviewer-details">
                  <h5 class="reviewer-name">Andrew Bartell</h5>
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
                <div class="reviewer-avatar">
<<<<<<< HEAD
                  <img src="images/reviews/emma-thompson.jpg" alt="Emma Thompson">
=======
                  <img src="" alt="Mike Rodriguez">
>>>>>>> 4c1d2a96c53e1d46d7887d7cbb547c21e28093e9
                </div>
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
              <p class="review-text">"Tracking my hydration and workouts has never been easier. The progress charts are incredibly motivating!"</p>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="review-card">
            <div class="review-header">
              <div class="reviewer-info">
                <div class="reviewer-avatar">
<<<<<<< HEAD
                  <img src="images/reviews/h-harold.png" alt="H. Harold">
=======
                  <img src="" alt="Emma Thompson">
>>>>>>> 4c1d2a96c53e1d46d7887d7cbb547c21e28093e9
                </div>
                <div class="reviewer-details">
                  <h5 class="reviewer-name">H. Harold</h5>
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
                <div class="reviewer-avatar">
<<<<<<< HEAD
                  <img src="images/reviews/alex-kim.jpg" alt="Alex Kim">
=======
                  <img src="" alt="Alex Kim">
>>>>>>> 4c1d2a96c53e1d46d7887d7cbb547c21e28093e9
                </div>
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
                <div class="reviewer-avatar">
<<<<<<< HEAD
                  <img src="images/reviews/steve-k.png" alt="Steve K.">
=======
                  <img src="" alt="Lisa Wang">
>>>>>>> 4c1d2a96c53e1d46d7887d7cbb547c21e28093e9
                </div>
                <div class="reviewer-details">
                  <h5 class="reviewer-name">Steve K.</h5>
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
