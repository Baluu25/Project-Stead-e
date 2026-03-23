<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class UpdateHabitRequest extends FormRequest
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
            'name'  => ['sometimes', 'required', 'string', 'max:255'],
            'description'   => ['nullable', 'string'],
            'category'  => ['sometimes', 'required', 'in:Nutrition,Fitness,Mindfulness,Study,Work'],
            'frequency' => ['sometimes', 'required', 'in:daily,weekly,monthly,custom'],
            'target_count'  => ['nullable', 'integer', 'min:1'],
            'icon'  => ['nullable', 'string', 'max:50'],
            'is_active' => ['nullable', 'boolean'],
        ];
    }
}
