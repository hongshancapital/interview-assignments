<?php
namespace App\Models;

use Illuminate\Support\Facades\Hash;
use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    CONST ORIGINAL_PASSWORD = '123456';

    // 后台用户表
    protected $table = 'users';
    protected $fillable = [
        "username", "password"
    ];


    public function setPasswordAttribute($value)
    {
        $this->attributes['password'] = Hash::make(empty($value) ? self::ORIGINAL_PASSWORD : $value);
    }


}

