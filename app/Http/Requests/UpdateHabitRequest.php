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
            'habit_id'    => ['required', 'integer', 'exists:habits,id'],
            'mood'        => ['nullable', 'in:happy,neutral,sad,energetic,tired'],
            'notes'       => ['nullable', 'string'],
            'is_skipped'  => ['nullable', 'boolean'],
        ];
    }
}
