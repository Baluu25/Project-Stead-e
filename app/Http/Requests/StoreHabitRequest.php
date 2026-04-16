<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class StoreHabitRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     */
    public function authorize(): bool
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array<string, \Illuminate\Contracts\Validation\ValidationRule|array<mixed>|string>
     */
    public function rules(): array
    {
        return [
            'name'        => ['required', 'string', 'max:255'],
            'description' => ['nullable', 'string'],
            'category'    => ['required', 'in:Nutrition,Fitness,Mindfulness,Study,Work'],
            'frequency'   => ['required', 'in:daily,weekly,monthly,custom'],
            'target_count'=> ['nullable', 'integer', 'min:1'],
            'unit'        => ['nullable', 'string', 'max:50'],
            'icon'        => ['nullable', 'string', 'max:50'],
            'goal_id' => ['nullable', 'integer', 'exists:goals,id']
        ];
    }
}
