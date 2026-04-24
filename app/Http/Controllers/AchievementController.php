<?php

namespace App\Http\Controllers;

use App\Models\Achievement;
use App\Http\Requests\StoreAchievementRequest;
use App\Http\Requests\UpdateAchievementRequest;

class AchievementController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $achievements = auth()->user()
            ->achievements()
            ->orderBy('achievement_type')
            ->orderBy('threshold_value')
            ->get()
            ->groupBy('achievement_type');
        $flat           = $achievements->flatten();
        $totalCount     = $flat->count();
        $completedCount = $flat->whereNotNull('unlocked_at')->count();

        return view('achievements', compact('achievements', 'totalCount', 'completedCount'));

    }
}
