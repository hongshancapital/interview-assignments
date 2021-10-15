<?php


namespace App\Repositories;


use App\Models\User;

interface UserRepositoryInterface
{
    public function createUser(array $data): User;
}
