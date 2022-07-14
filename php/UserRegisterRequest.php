<?php
namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Support\Facades\Validator;

class UserRegisterRequest extends FormRequest
{
    public function rules()
    {
        //只能以英文字母或下划线开头,只能包含英文字母，下划线或数字
        Validator::extend('username_chars', function ($attribute, $value, $parameters, $validator) {
            return preg_match('/^[_a-zA-Z][_a-zA-Z0-9]*$/', $value);
        }, '用户名只能以英文字母或下划线开头,只能包含英文字母，下划线或数字');
        //不能含有 3 位或以上的连续数字，如 123、234 等
        Validator::extend('password_numbers', function ($attribute, $value, $parameters, $validator) {
            return !preg_match('/012|123|234|345|456|567|678|789/', $value);
        }, '密码不能含有 3 位或以上的连续数字，如 123、234 等');
        //必须有大写字母，小写字母或数字中的两项
        Validator::extend('password_chars', function ($attribute, $value, $parameters, $validator) {
            return preg_match('/^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)[\da-zA-Z]*$/', $value);
        }, '密码必须有大写字母，小写字母或数字中的两项');

        return [
            'Username' => 'required|username_chars|max:32',
            'Password' => 'required|min:6|max:32|password_chars|password_numbers',
            'RepeatPassword' => 'required|same:Password',
        ];
    }
}