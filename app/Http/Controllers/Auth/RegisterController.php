<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\Models\User;
use App\Models\Achievement;
use Illuminate\Foundation\Auth\RegistersUsers;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class RegisterController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Register Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles the registration of new users as well as their
    | validation and creation. By default this controller uses a trait to
    | provide this functionality without requiring any additional code.
    |
    */

    use RegistersUsers;

    /**
     * Where to redirect users after registration.
     *
     * @var string
     */
    protected $redirectTo = '/home';

    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('guest');
    }

    /**
     * Get a validator for an incoming registration request.
     *
     * @param  array  $data
     * @return \Illuminate\Contracts\Validation\Validator
     */
    protected function validator(array $data)
    {
        return Validator::make($data, [
            'name' => ['required', 'string', 'max:255'],
            'username' => ['required', 'string', 'max:255', 'unique:users'],
            'email' => ['required', 'string', 'email', 'max:255', 'unique:users'],
            'password' => ['required', 'string', 'min:8'],
            'user_goal' => ['required', 'in:weight_loss,consistency,quit_habit,explore'],
            'preferred_categories'  => ['required', 'array', 'min:1'],
            'preferred_categories.*'=> ['in:Fitness,Nutrition,Mindfulness,Study,Work'],
        ]);
    }


    /**
     * Create a new user instance after a valid registration.
     *
     * @param  array  $data
     * @return \App\Models\User
     */
    protected function create(array $data)
    {
        $user = User::create([
            'name' => $data['name'],
            'username' => $data['username'],
            'email'  => $data['email'],
            'password' => Hash::make($data['password']),
            'user_goal'  => $data['user_goal'],
            'preferred_categories' => $data['preferred_categories'],
        ]);

        $achievements = [
            ['Streaks',     'First step',           'Complete your first day', 1],
            ['Streaks',     'Getting Warmed Up',    'Keep your habit alive for 3 days straight.', 3],
            ['Streaks',     'Locked In',            'Reach a 7-day streak', 7],
            ['Milestones',  'Habit Formed',         'Complete a habit 10 times total', 10],
            ['Milestones',  'On a Roll',            'Complete 50 habits total', 50],
            ['Milestones',  'Century Club',         'Complete 100 habits total', 100],
            ['Nutrition',   'First Bite',           'Log your first nutrition habit', 1],
            ['Nutrition',   'Hydration Hero',       'Log water intake 7 days in a row', 7],
            ['Nutrition',   'Clean Plate',          'Track all meals for 5 full days', 5],
            ['Fitness',     'First Sweat',          'Complete your first workout habit', 1],
            ['Fitness',     'Weekly Warrior',       'Work out 3 times in one week', 3],
            ['Fitness',     'Iron Will',            'Complete 30 fitness habits', 30],
            ['Mindfulness', 'First Breath',         'Complete your first mindfulness session', 1],
            ['Mindfulness', 'Finding Peace',        'Complete 3 mindfulness sessions', 3],
            ['Mindfulness', 'Zen Master',           'Complete 7 mindfulness sessions', 7],
            ['Study',       'First Focus',          'Log your first study session', 1],
            ['Study',       'Study Streak',         'Study 5 days in a row', 5],
            ['Study',       'Consistency Builder',  'Study 10 total hours', 10],
            ['Work',        'First Task',           'Complete your first work task', 1],
            ['Work',        'Getting Productive',   'Complete 5 tasks', 5],
            ['Work',        'Work Warrior',         'Complete 10 tasks', 10],
        ];

        foreach ($achievements as [$type, $name, $description, $threshold]) {
            Achievement::create([
                'user_id'          => $user->id,
                'achievement_type' => $type,
                'name'             => $name,
                'description'      => $description,
                'threshold_value'  => $threshold,
                'progress'         => 0,
                'unlocked_at'      => null,
            ]);
        }

        return $user;
    }

    public function showRegistrationForm()
    {
        return view('auth.register');
    }
}
