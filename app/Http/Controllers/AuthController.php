<?php

namespace App\Http\Controllers;

use App\Models\User;
use Database\Seeders\AchievementSeeder;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Validation\Rules\Password;

class AuthController extends Controller
{
    public function register(Request $request)
    {
        $data = $request->validate([
            'name'      => ['required', 'string', 'max:255'],
            'username'  => ['required', 'string', 'max:255', 'unique:users'],
            'email'     => ['required', 'email', 'unique:users'],
            'password'  => ['required', 'confirmed', Password::defaults()],
            'gender'    => ['nullable', 'in:male,female,other'],
            'birthdate' => ['nullable', 'date'],
        ]);

        $user = User::create([
            'name'      => $data['name'],
            'username'  => $data['username'],
            'email'     => $data['email'],
            'password'  => Hash::make($data['password']),
            'gender'    => $data['gender'] ?? null,
            'birthdate' => $data['birthdate'] ?? null,
        ]);

        // Seed the default achievement rows for this brand-new user
        AchievementSeeder::seedForUser($user->id);

        $token = $user->createToken('android-app')->plainTextToken;

        return response()->json([
            'user'  => $user,
            'token' => $token,
        ], 201);
    }

    public function login(Request $request)
    {
        $data = $request->validate([
            'email'    => ['required', 'email'],
            'password' => ['required'],
        ]);

        if (!Auth::attempt($data)) {
            return response()->json([
                'message' => 'Hibás email vagy jelszó.',
            ], 401);
        }

        $user  = Auth::user();

        // Ensure every user always has all achievement rows (handles older accounts
        // that were registered before the seeding logic existed).
        AchievementSeeder::seedForUser($user->id);

        $token = $user->createToken('android-app')->plainTextToken;

        return response()->json([
            'user'  => $user,
            'token' => $token,
        ]);
    }

    public function logout(Request $request)
    {
        $request->user()->currentAccessToken()->delete();

        return response()->json(['message' => 'Kijelentkezve.']);
    }

    public function user(Request $request)
    {
        return response()->json($request->user());
    }
}
