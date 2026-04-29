@extends('layouts.app')

@section('title', 'Terms of Service')

@section('content')
<div class="container py-5" style="max-width: 860px;">
    <h1 class="mb-2 fw-bold">Terms of Service</h1>
    <p class="text-muted mb-5">Last updated: April 29, 2026</p>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">1. Acceptance of Terms</h2>
        <p>By accessing or using the Stead-E application ("Service"), you agree to be bound by these Terms of Service. If you do not agree to these terms, please do not use the Service.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">2. Use of the Service</h2>
        <p>Stead-E is a personal habit tracking and goal management platform. You may use the Service only for lawful purposes and in accordance with these Terms. You agree not to:</p>
        <ul>
            <li>Use the Service in any way that violates applicable laws or regulations.</li>
            <li>Attempt to gain unauthorized access to any part of the Service or its infrastructure.</li>
            <li>Interfere with or disrupt the integrity or performance of the Service.</li>
            <li>Use automated scripts or bots to interact with the Service without our prior consent.</li>
        </ul>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">3. Accounts</h2>
        <p>You are responsible for maintaining the confidentiality of your account credentials and for all activities that occur under your account. You must notify us immediately of any unauthorized use of your account. Stead-E is not liable for any loss resulting from unauthorized access due to your failure to keep your credentials secure.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">4. Intellectual Property</h2>
        <p>All content, features, and functionality of the Service — including but not limited to text, graphics, logos, and software — are the exclusive property of Stead-E and are protected by applicable intellectual property laws. You may not reproduce, distribute, or create derivative works without our express written permission.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">5. Disclaimer of Warranties</h2>
        <p>The Service is provided on an "as is" and "as available" basis without warranties of any kind, either express or implied. We do not warrant that the Service will be uninterrupted, error-free, or free of harmful components.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">6. Limitation of Liability</h2>
        <p>To the fullest extent permitted by law, Stead-E shall not be liable for any indirect, incidental, special, consequential, or punitive damages arising from your use of or inability to use the Service.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">7. Changes to These Terms</h2>
        <p>We reserve the right to modify these Terms at any time. We will notify users of significant changes by updating the "Last updated" date at the top of this page. Continued use of the Service after changes constitutes your acceptance of the revised Terms.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">8. Governing Law</h2>
        <p>These Terms shall be governed by and construed in accordance with applicable laws. Any disputes arising under these Terms shall be subject to the exclusive jurisdiction of the competent courts.</p>
    </section>

    <section class="mb-5">
        <h2 class="h4 fw-semibold mb-3">9. Contact</h2>
        <p>If you have any questions about these Terms, please <a href="{{ route('contact') }}">contact us</a>.</p>
    </section>
</div>
@endsection
