<?php

declare (strict_types=1);
namespace App\Model;

use Hyperf\DbConnection\Model\Model;
/**
 * @property int $id
 * @property string $username
 * @property string $password
 * @property string $access_token
 * @property string $created_time
 */
class YUser extends Model
{
    /**
     * The table associated with the model.
     *
     * @var string
     */
    protected $table = 'user';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = ['id', 'username', 'password', 'access_token', 'created_at'];
}