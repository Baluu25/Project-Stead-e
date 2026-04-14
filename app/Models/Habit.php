<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Habit extends Model
{
    use HasFactory;

    /**
     * The table associated with the model.
     *
     * @var string
     */
    protected $table = 'habits';

    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'user_id',
        'name',
        'description',
        'category',
        'frequency',
        'target_count',
        'icon',
        'is_active',
        'goal_id'
    ];

    public function completions() {
    return $this->hasMany(HabitCompletion::class);
    }

    public function goal() { 
        return $this->belongsTo(Goal::class); 
    }

}