<?php


namespace App\Rules;


use Illuminate\Contracts\Validation\Rule;

class RepeatPasswordRule implements Rule
{
    private array $data;

    private string $message;

    public function __construct(array $data)
    {
        $this->data = $data;
    }


    public function passes($attribute, $value): bool
    {
        if ($this->data['password'] != $value) {
            $this->message = '两次密码输入不相同';
            return false;
        }

        return true;
    }

    public function message(): string
    {
        return $this->message;
    }
}
