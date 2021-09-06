<?php

namespace App\Models;

use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;

class UserModel extends Authenticatable
{
    use HasFactory, Notifiable;

    protected $table = 'user';

    protected $fillable = [
        'username',
        'password',
        'salt'
    ];

    public function getAuthPassword()
    {
        return ['password' => $this->attributes['password'], 'salt' => $this->attributes['salt']];
    }
}
