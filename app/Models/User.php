<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Database\Eloquent\Relations\HasOne;
use Laravel\Sanctum\HasApiTokens;

class User extends Authenticatable
{
    use HasFactory;
    use HasApiTokens;

    protected $table = 'users';
    
    protected $fillable = [
        'name',
        'username',
        'profile_picture',
        'email',
        'password',
        'gender',
        'birthdate',
        'weight',
        'height',
        'sleep_time',
        'wake_time',
        'user_goal',
        'preferred_categories',
        'is_admin'
    ];

    protected $hidden = [
        'password_hash',
    ];

    protected $casts = [
        'created_at' => 'datetime',
        'updated_at' => 'datetime',
        'preferred_categories' => 'array',
        'is_admin' => 'boolean'
    ];

    public function habits(): HasMany
    {
        return $this->hasMany(Habit::class);
    }

    public function habitCompletions(): HasMany
    {
        return $this->hasMany(HabitCompletion::class);
    }

    public function achievements(): HasMany
    {
        return $this->hasMany(Achievement::class);
    }

    public function settings(): HasOne
    {
        return $this->hasOne(UserSetting::class);
    }

    public function goals() {
    return $this->hasMany(Goal::class);
    }
}