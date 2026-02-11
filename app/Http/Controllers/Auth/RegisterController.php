<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\Models\User;
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
            'gender' => ['required', 'in:male,female,other'],
            'birthdate' => ['required', 'date', 'before:today'],
            'weight' => ['required', 'numeric', 'min:30', 'max:300'],
            'height' => ['required', 'integer', 'min:100', 'max:250'],
            'sleep_time' => ['required', 'date_format:H:i'],
            'wake_time' => ['required', 'date_format:H:i'],
            'user_goal' => ['required', 'in:weight_loss,consistency,quit_habit,explore'],
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
        return User::create([
            'name' => $data['name'],
            'username' => $data['username'],
            'email' => $data['email'],
            'password' => Hash::make($data['password']),
            'gender' => $data['gender'],
            'birthdate' => $data['birthdate'],
            'weight' => $data['weight'],
            'height' => $data['height'],
            'sleep_time' => $data['sleep_time'],
            'wake_time' => $data['wake_time'],
            'user_goal' => $data['user_goal'],
        ]);
    }

    public function showRegistrationForm()
    {
        return view('auth.register');
    }
}
