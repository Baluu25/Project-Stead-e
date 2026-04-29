<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class UpdateGoalRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'title'        => 'sometimes|required|string|max:255',
            'description'  => 'nullable|string',
            'icon'         => 'nullable|string|max:100',
            'category'     => 'sometimes|nullable|string',
            'target_value' => 'sometimes|required|integer|min:1',
            'unit'         => 'sometimes|nullable|string',
            'deadline'     => 'nullable|date|after_or_equal:today',
        ];
    }
}
