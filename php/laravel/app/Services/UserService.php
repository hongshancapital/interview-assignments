<?php


namespace App\Services;


use App\Repositories\UserRepository;
use App\Repositories\UserRepositoryInterface;

class UserService
{
    private UserRepositoryInterface $userRepository;
    public function __construct(UserRepositoryInterface $userRepository)
    {
        $this->userRepository = $userRepository;
    }

    public function register(array $data)
    {
        $this->userRepository->createUser($data);
    }
}
