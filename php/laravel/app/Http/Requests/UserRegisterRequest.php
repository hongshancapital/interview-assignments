<?php


namespace App\Http\Requests;


use App\Rules\PasswordRule;
use App\Rules\RepeatPasswordRule;
use App\Rules\UsernameRule;
use Illuminate\Foundation\Http\FormRequest;

class UserRegisterRequest extends FormRequest
{
    protected $redirectRoute = '';
    /**
     * Determine if the user is authorized to make this request.
     */
    public function authorize(): bool
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     */
    public function rules(): array
    {
        return [
            'username' => ['required' ,'max:20', new UsernameRule(),'unique:users,username'],
            'password' => ['required' ,'min:6', 'max:20', new PasswordRule()],
            'repeat_password' => ['required','min:6', 'max:20', new RepeatPasswordRule($this->all())],
        ];
    }

    public function attributes(): array
    {
        return [
            'username' => '用户名',
            'password' => '密码',
            'repeat_password' => '重复密码',
        ];
    }

    public function messages(): array
    {
        return [
            'username.required' => '用户名不存在',
            'password.required' => '密码不存在',
            'repeat_password.required' => '重复密码不存在',
            'username.max' => '用户名不能超过20位',
            'password.max' => '密码不能超过20位',
            'repeat_password.max' => '重复密码不能超过20位',
            'password.min' => '密码不能小于6位',
            'repeat_password.min' => '重复密码不能小于6位',
            'username.unique' => '用户名重复',
        ];
    }
}
