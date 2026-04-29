<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Goal extends Model
{
    use HasFactory;

    protected $fillable = [
        'user_id', 'title', 'description', 'icon',
        'category', 'target_value', 'current_value',
        'unit', 'deadline', 'status',
    ];

    protected $casts = [
        'deadline'      => 'date',
        'target_value'  => 'integer',
        'current_value' => 'integer',
    ];

    public function user()
    {
        return $this->belongsTo(User::class);
    }

    public function getProgressAttribute(): int
    {
        if ($this->target_value == 0) return 0;
        return min(100, (int)(($this->current_value / $this->target_value) * 100));
    }

    public function habits()
    {
        return $this->hasMany(Habit::class);
    }
}
