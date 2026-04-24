<?php

namespace App\Http\Controllers;

use App\Models\Habit;
use App\Models\Goal;
use App\Models\User;
use Illuminate\Http\Request;

class AdminController extends Controller
{
    public function index()
    {
        $users = User::withCount(['habits', 'goals', 'achievements'])
            ->orderBy('created_at', 'desc')
            ->get();

        $stats = [
            'total_users'  => User::count(),
            'admin_count'  => User::where('is_admin', true)->count(),
            'total_habits' => Habit::count(),
            'total_goals'  => Goal::count(),
        ];

        return view('admin', compact('users', 'stats'));
    }

    public function destroy(User $user)
    {
        if ($user->id === auth()->id()) {
            return redirect()->route('admin')
                ->with('error', 'You cannot delete your own account.');
        }

        $user->delete();

        return redirect()->route('admin')
            ->with('success', 'User "' . $user->username . '" deleted successfully.');
    }

    public function promote(User $user)
    {
        if ($user->id === auth()->id()) {
            return redirect()->route('admin')
                ->with('error', 'You cannot change your own role.');
        }

        $user->update(['is_admin' => true]);

        return redirect()->route('admin')
            ->with('success', 'User "' . $user->username . '" is now an admin.');
    }

    public function demote(User $user)
    {
        if ($user->id === auth()->id()) {
            return redirect()->route('admin')
                ->with('error', 'You cannot change your own role.');
        }

        $user->update(['is_admin' => false]);

        return redirect()->route('admin')
            ->with('success', 'Admin role removed from "' . $user->username . '".');
    }
}