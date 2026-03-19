import { defineConfig } from 'vite';
import laravel from 'laravel-vite-plugin';

export default defineConfig({
    plugins: [
        laravel({
            input: [
                'resources/sass/app.scss',

                // Js
                'resources/js/app.js',
                'resources/js/register.js',
                'resources/js/login.js',
                'resources/js/script.js',

                // Css
                'resources/css/splash.css',
                'resources/css/index.css',
                'resources/css/register.css',
                'resources/css/login.css',
                'resources/css/dashboard.css',
                'resources/css/dashboard_home.css'
            ],
            refresh: true,
        }),
    ],
});
