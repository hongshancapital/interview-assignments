<?php
namespace App\Services;

use Illuminate\Support\Str;
use App\Models\UserModel;
use Illuminate\Support\Facades\Hash;

class UserService
{
    public function __construct(UserModel $userModel)
    {
        $this->model = $userModel;
    }

    public function register($username, $password)
    {
        $this->verifyExist($username);
        $salt = $this->generateSalt();
        $password = $this->generatePassword($password, $salt);
        $user = $this->model->create([
            'username' => $username,
            'password' => $password,
            'salt' => $salt
        ]);
        return $user;
    }

    public function verifyExist($username){
        $existUser = $this->model->where('username', $username)->first();
        if($existUser){
            throw new \Exception("该用户名已被注册");
        }
    }

    public function generateSalt()
    {
        return Str::random(6);
    }

    public function generatePassword($password, $salt)
    {
        return Hash::make($password . $salt);
    }
}
