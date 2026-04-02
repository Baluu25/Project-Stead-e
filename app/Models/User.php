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
        'email',
        'password',
        'gender',
        'birthdate',
        'weight',
        'height',
        'sleep_time',
        'wake_time',
        'user_goal',
    ];

    protected $hidden = [
        'password_hash',
    ];

    protected $casts = [
        'created_at' => 'datetime',
        'updated_at' => 'datetime',
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

    public function reminders(): HasMany
    {
        return $this->hasMany(Reminder::class);
    }

    public function notifications(): HasMany
    {
        return $this->hasMany(Notification::class);
    }

    public function settings(): HasOne
    {
        return $this->hasOne(UserSetting::class);
    }

    public function friends(): HasMany
    {
        return $this->hasMany(Friend::class, 'user_id');
    }

    public function friendOf(): HasMany
    {
        return $this->hasMany(Friend::class, 'friend_id');
    }

    public function createdChallenges(): HasMany
    {
        return $this->hasMany(Challenge::class, 'created_by_id');
    }

    public function goals() {
    return $this->hasMany(Goal::class);
    }
}