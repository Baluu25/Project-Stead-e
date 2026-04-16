<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class HabitCompletion extends Model
{
    /** @use HasFactory<\Database\Factories\HabitCompletionFactory> */
    use HasFactory;

    protected $table = 'habit_completions';
    protected $fillable = ['completed_at', 'quantity', 'is_skipped', 'habit_id', 'user_id'];

    public function habit() {
    return $this->belongsTo(Habit::class);
    }   
}
