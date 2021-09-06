<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\UserRegisterRequest;
use App\Services\UserService;

class UserController extends Controller
{
    public function index()
    {
        return view('user.register');
    }

    public function register(UserRegisterRequest $request, UserService $userService)
    {
        try{
            $user = $userService->register($request->Username, $request->Password);
        }catch(\Exception $e){
            return response()->json([
                'message' => $e->getMessage()
            ], 422);
        }
        
        return response()->json([
            'message' => '注册成功'
        ]);
    }
}
