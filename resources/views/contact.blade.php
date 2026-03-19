@extends('layouts.app')

@section('title', 'Contact us')

@section('styles')
    <link rel="stylesheet" href="{{ asset('storage/css/contact.css') }}">
@endsection

@section('content')
    <div class="contact-container">
        <div class="contact-card">
            
            <h1 class="contact-title">Contact us</h1>
            <p class="contact-subtitle">We'd love to hear from you. Send us a message and we'll respond as soon as possible.</p>
            
            <!-- Display validation errors -->
            @if ($errors->any())
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <ul class="mb-0">
                        @foreach ($errors->all() as $error)
                            <li>{{ $error }}</li>
                        @endforeach
                    </ul>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            @endif

            <!-- Display session messages -->
            @if(session('error'))
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    {{ session('error') }}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            @endif

            @if(session('success'))
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    {{ session('success') }}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            @endif
            
            <form method="POST" action="">
                @csrf
                
                <div class="form-group">
                    <label for="name" class="form-label">Full Name</label>
                    <input type="text" 
                           id="name" 
                           name="name" 
                           class="form-control @error('name') is-invalid @enderror" 
                           value="{{ old('name') }}" 
                           placeholder="Enter your full name"
                           required 
                           autofocus>
                    @error('name')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
                
                <div class="form-group">
                    <label for="email" class="form-label">Email Address</label>
                    <input type="email" 
                           id="email" 
                           name="email" 
                           class="form-control @error('email') is-invalid @enderror" 
                           value="{{ old('email') }}" 
                           placeholder="Enter your email address"
                           required>
                    @error('email')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="message" class="form-label">Message</label>
                    <textarea 
                           id="message" 
                           name="message" 
                           class="form-control @error('message') is-invalid @enderror" 
                           rows="3"
                           placeholder="Type your message here..."
                           required>{{ old('message') }}</textarea>
                    @error('message')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
                
                <button type="submit" class="btn btn-primary mt-3">Send Message</button>
            </form>
        </div>
    </div>
@endsection