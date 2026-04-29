<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class HabitCompletion extends Model
{
    use HasFactory, SoftDeletes;

    protected $table = 'habit_completions';

    protected $fillable = ['completed_at', 'quantity', 'is_skipped', 'habit_id', 'user_id'];

    protected $casts = [
        'completed_at' => 'datetime',
    ];

    public function habit()
    {
        return $this->belongsTo(Habit::class);
    }
}
