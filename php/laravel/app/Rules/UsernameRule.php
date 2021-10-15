<?php


namespace App\Rules;


use Illuminate\Contracts\Validation\Rule;

class UsernameRule implements Rule
{
    private string $message;

    public function passes($attribute, $value)
    {
        if (!preg_match('/^[a-zA-Z_]/', $value)) {
            $this->message = '用户名只能以英文字母或下划线开头';
            return false;
        }

        if (!preg_match('/^[_0-9a-zA-Z]*$/', $value)) {
            $this->message = '用户名只能包含英文字母，下划线或数字';
            return false;
        }

        return true;
    }

    public function message()
    {
        return $this->message;
    }
}
