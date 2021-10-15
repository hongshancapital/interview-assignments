<?php


namespace App\Http\Controllers;


use App\Http\Requests\UserRegisterRequest;
use App\Services\UserService;

class UserController extends Controller
{
    public function register(UserRegisterRequest $request, UserService $service)
    {
        $service->register($request->all());
        return response('注册成功');
    }
}
