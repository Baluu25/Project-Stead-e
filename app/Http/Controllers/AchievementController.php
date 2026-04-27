<?php

namespace App\Http\Controllers;

use App\Models\Achievement;
use App\Http\Requests\StoreAchievementRequest;
use App\Http\Requests\UpdateAchievementRequest;
use Illuminate\Http\Request;

class AchievementController extends Controller
{
    /**
     * Web view – used by the Blade front-end.
     */
    public function webIndex()
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

    /**
     * API endpoint – returns a flat JSON array consumed by the Android app.
     * Each achievement is serialised with the fields the mobile client expects:
     *   id, title, description, icon, achievement_type,
     *   is_unlocked, unlocked_at, progress, threshold_value
     */
    public function index(Request $request)
    {
        // If the request wants JSON (mobile / API client) → return JSON.
        // If it comes from a browser (web front-end) → keep the old Blade view.
        if ($request->expectsJson() || $request->is('api/*')) {
            $achievements = auth()->user()
                ->achievements()
                ->orderBy('achievement_type')
                ->orderBy('threshold_value')
                ->get()
                ->map(fn($a) => [
                    'id'               => $a->id,
                    'title'            => $a->name,           // DB column is "name"
                    'description'      => $a->description,
                    'icon'             => $a->icon,
                    'achievement_type' => $a->achievement_type,
                    'is_unlocked'      => $a->unlocked_at !== null,
                    'unlocked_at'      => $a->unlocked_at,
                    'progress'         => (float) $a->progress,
                    'threshold_value'  => (int)   $a->threshold_value,
                ]);

            return response()->json($achievements);
        }

        // Web fallback (shouldn't normally be hit via the web route any more,
        // but kept for safety so the Blade page doesn't break).
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
