<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Goal extends Model {
    protected $fillable = [
        'user_id', 'title', 'description', 'icon',
        'category', 'target_value',
        'unit', 'deadline',
    ];

    protected $casts = ['deadline' => 'date'];

    public function user() {
        return $this->belongsTo(User::class);
    }

    // Progress percentage helper
    public function getProgressAttribute(): int {
        if ($this->target_value == 0) return 0;
        return min(100, (int)(($this->current_value / $this->target_value) * 100));
    }
}
