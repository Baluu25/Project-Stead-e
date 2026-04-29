<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stead-E | @yield('title')</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" 
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" 
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" 
            crossorigin="anonymous" defer></script>
    <script src="https://kit.fontawesome.com/0bf932704c.js" crossorigin="anonymous" defer></script>
    <link rel="stylesheet" href="{{ asset('storage/css/app.css') }}">
    <script src="{{ asset('storage/js/script.js') }}" defer></script>
    @yield('styles')
</head>
<body>
   <nav class="navbar navbar-expand-lg navbar-light px-4 glass-card">
    <div class="container-fluid px-0 px-lg-3">
        <a class="navbar-brand align-items-center" href="{{ url('/') }}" aria-label="Home">
            <img src="{{ asset('images/stead-e_logo.png') }}" alt="logo" width="40" height="40" />
            <span>Stead-E</span>
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent"
                aria-controls="navbarContent" aria-expanded="false" aria-label="Navigáció váltása">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0 align-items-center">
                <li class="nav-item"><a class="nav-link" onclick="window.location.hash='functions'">Functions</a></li>
                <li class="nav-item"><a class="nav-link" onclick="window.location.hash='download'">Download</a></li>
                <li class="nav-item"><a class="nav-link" onclick="window.location.hash='reviews'">Reviews</a></li>

                @guest
                    <li class="nav-item ms-lg-3 mt-2 mt-lg-0">
                        <a href="{{ route('login') }}" class="btn btn-primary btn-start">Login</a>
                    </li>
                @else
                    <li class="nav-item dropdown ms-lg-3 mt-2 mt-lg-0">
                        <a class="nav-link dropdown-toggle align-items-center" href="#" role="button" 
                           data-bs-toggle="dropdown" aria-expanded="false">
                            {{ Auth::user()->name }}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="{{ route('home') }}">Dashboard</a></li>
                            <li><a class="dropdown-item" href="{{ route('profile') }}">Profile</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <form method="POST" action="{{ route('logout') }}">
                                    @csrf
                                    <button type="submit" class="dropdown-item btn-primary">Logout</button>
                                </form>
                            </li>
                        </ul>
                    </li>
                @endguest
            </ul>
        </div>
    </div>
</nav>
    
    <main>
        @yield('content')
    </main>
    
    <footer class="footer py-5">
        <div class="container">
            <div class="row">
                <div class="col-lg-4 mb-4 mb-lg-0">
                    <div class="footer-brand d-flex align-items-center mb-3">
                        <img src="{{ asset('images/stead-e_logo.png') }}" alt="Stead-E Logo" width="40" height="40" />
                        <span class="ms-2">Stead-E</span>
                    </div>
                    <p class="footer-text">Your all-in-one fitness partner. Track hydration, exercise, and health metrics together.</p>
                    <div class="social-links mt-4">
                        <a href="#" class="social-link" aria-label="Facebook">
                            <i class="fab fa-facebook"></i>
                        </a>
                        <a href="#" class="social-link" aria-label="Twitter">
                            <i class="fab fa-twitter"></i>
                        </a>
                        <a href="#" class="social-link" aria-label="Instagram">
                            <i class="fab fa-instagram"></i>
                        </a>
                    </div>
                </div>
                <div class="col-lg-2 col-md-4 mb-4 mb-md-0">
                    <h5 class="footer-heading">Product</h5>
                    <ul class="footer-links">
                        <li><a href="#funkciok">Features</a></li>
                        <li><a href="#letoltes">Download</a></li>
                        <li><a href="#velemenyek">Reviews</a></li>
                    </ul>
                </div>
                <div class="col-lg-2 col-md-4 mb-4 mb-md-0">
                    <h5 class="footer-heading">Support</h5>
                    <ul class="footer-links">
                        <li><a href="{{ url("/contact") }}">Contact Us</a></li>
                        <li><a href="{{ route('privacy') }}">Privacy Policy</a></li>
                        <li><a href="{{ route('terms') }}">Terms of Service</a></li>
                    </ul>
                </div>
                <div class="col-lg-2 col-md-12">
                    <h5 class="footer-heading">Download</h5>
                    <div class="footer-download">
                        <a href="#" class="google-play-btn">
                            <img src="{{ asset('images/google_play_badge.webp') }}" alt="Get it on Google Play" class="play-store-badge" width="135">
                        </a>
                    </div>
                </div>
            </div>
            <div class="footer-bottom mt-5 pt-4 border-top">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <p class="footer-copyright">© {{ date('Y') }} Stead-E. All rights reserved.</p>
                    </div>
                    <div class="col-md-6 text-md-end">
                        <div class="footer-legal-links">
                            <a href="{{ route('privacy') }}">Privacy Policy</a>
                            <a href="{{ route('terms') }}">Terms of Service</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>
</body>
</html>