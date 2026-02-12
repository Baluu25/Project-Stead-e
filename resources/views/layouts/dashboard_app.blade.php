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
    @vite(['resources/css/dashboard.css'])
    @vite(['resources/js/script.js'])
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark px-4 glass-card">
        <div class="container">
            <a class="navbar-brand d-flex align-items-center" href="{{ route('dashboard') }}" aria-label="Home">
                <img src="{{ asset('images/logo.png') }}" alt="logo" width="40" height="40" />
                <span>Stead-E</span>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent"
                    aria-controls="navbarContent" aria-expanded="false" aria-label="Navigáció váltása">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-end" id="navbarContent">
                <ul class="navbar-nav mb-2 mb-lg-0 align-items-center">
                    <li class="nav-item">
                        <a class="nav-link" href="{{ route('profile.edit') }}">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Settings</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-logout" href="{{ route("logout") }}">Logout</a>
                    </li>
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
                        <img src="{{ asset('images/logo.png') }}" alt="Stead-E Logo" width="40" height="40" />
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
                        <li><a href="#">Contact Us</a></li>
                        <li><a href="#">Privacy Policy</a></li>
                        <li><a href="#">Terms of Service</a></li>
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
                            <a href="#">Privacy Policy</a>
                            <a href="#">Terms of Service</a>
                            <a href="#">Cookie Policy</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>
</body>
</html>