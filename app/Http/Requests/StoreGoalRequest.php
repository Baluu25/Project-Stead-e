<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class StoreGoalRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'title'        => 'required|string|max:255',
            'description'  => 'nullable|string',
            'icon'         => 'nullable|string|max:100',
            'category'     => 'required|string',
            'target_value' => 'required|integer|min:1',
            'unit'         => 'required|string',
            'deadline'     => 'nullable|date|after_or_equal:today',
        ];
    }
}
