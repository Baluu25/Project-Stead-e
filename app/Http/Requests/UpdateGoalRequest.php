<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class UpdateGoalRequest extends FormRequest
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
            'title'        => 'sometimes|required|string|max:255',
            'description'  => 'nullable|string',
            'icon'         => 'nullable|string|max:100',
            'category'     => 'sometimes|nullable|string',
            'target_value' => 'sometimes|required|integer|min:1',
            'unit'         => 'sometimes|nullable|string',
            'deadline'     => 'nullable|date|after:today',
        ];
    }
}
