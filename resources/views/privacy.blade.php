@extends('layouts.app')

@section('title', 'Privacy Policy')

@section('content')
<div class="container py-5" style="max-width: 860px;">
    <h1 class="mb-2 fw-bold">Privacy Policy</h1>
    <p class="text-muted mb-5">Last updated: April 29, 2026</p>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">1. Introduction</h2>
        <p>Stead-E ("we", "us", or "our") is committed to protecting your personal information. This Privacy Policy explains what data we collect, how we use it, and your rights regarding that data when you use our Service.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">2. Information We Collect</h2>
        <p>We collect the following types of information when you use Stead-E:</p>
        <ul>
            <li><strong>Account information:</strong> Your name and email address when you register.</li>
            <li><strong>Usage data:</strong> Habits, goals, completions, and achievements you create and track within the app.</li>
            <li><strong>Profile data:</strong> Any optional profile information you choose to provide (e.g., profile picture).</li>
            <li><strong>Technical data:</strong> Basic server logs (IP address, browser type) for security and diagnostic purposes.</li>
        </ul>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">3. How We Use Your Information</h2>
        <p>We use the information we collect solely to:</p>
        <ul>
            <li>Provide, operate, and maintain the Service.</li>
            <li>Display your personal progress, statistics, and achievements.</li>
            <li>Respond to your support or contact requests.</li>
            <li>Ensure the security and integrity of the Service.</li>
        </ul>
        <p>We do not use your data for advertising or sell it to third parties.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">4. Data Sharing</h2>
        <p>We do not sell, trade, or otherwise transfer your personal information to third parties. Your habit and goal data is private and visible only to you and, where applicable, to administrators for support purposes.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">5. Data Retention</h2>
        <p>We retain your personal data for as long as your account is active. If you delete your account, your data will be permanently removed from our systems within 30 days, except where retention is required by law.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">6. Your Rights</h2>
        <p>You have the right to:</p>
        <ul>
            <li>Access the personal data we hold about you.</li>
            <li>Request correction of inaccurate data.</li>
            <li>Request deletion of your account and associated data.</li>
            <li>Object to or restrict certain processing of your data.</li>
        </ul>
        <p>To exercise any of these rights, please <a href="{{ route('contact') }}">contact us</a>.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">7. Security</h2>
        <p>We implement industry-standard security measures to protect your data, including encrypted storage of passwords and HTTPS transmission. However, no method of transmission over the internet is 100% secure, and we cannot guarantee absolute security.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">8. Changes to This Policy</h2>
        <p>We may update this Privacy Policy from time to time. We will notify you of significant changes by updating the "Last updated" date at the top of this page. Continued use of the Service after changes constitutes acceptance of the updated policy.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">9. Contact</h2>
        <p>If you have any questions or concerns about this Privacy Policy, please <a href="{{ route('contact') }}">contact us</a>.</p>
    </section>
</div>
@endsection
