<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="{{ asset('storage/css/splash.css') }}">
    <script src="{{ asset('storage/js/script.js') }}" defer></script>
    <title>Stead-E - Welcome</title>
</head>
<body>
    <div class="splash-container">
        <div class="logo">
            <img src="images/logo.png" alt="logo">
        </div>
        <h1>Stead-E</h1>
        <p class="tagline">Build Better Habits</p>
        <div class="loading-bar">
            <div class="loading-progress"></div>
        </div>
    </div>

    <script>
        setTimeout(function() {
            window.location.href = 'index';
        }, 3000);
    </script>
</body>
</html>