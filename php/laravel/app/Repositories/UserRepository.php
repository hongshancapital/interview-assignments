<?php


namespace App\Repositories;


use App\Models\User;
use Illuminate\Support\Str;

class UserRepository implements UserRepositoryInterface
{
    private function getSalt(): string
    {
        return Str::random(6);
    }

    public function createUser(array $data): User
    {

        $user = new User();
        $user->salt = $this->getSalt();
        $user->username = $data['username'];
        $user->password = md5(md5(trim($data['username'])).$user->salt) ;
        $user->save();
        return $user;
    }
}
