<?php
declare (strict_types=1);

/**
 * 开发: Atom
 * 时间: 2021-05-15
 */

namespace app\services\User;


use app\api\models\InterviewUser;

/**
 * 用户类服务层
 * Class UserRegisterService
 * @package app\services\User
 */
class UserRegisterService
{

    public function create($params)
    {
        return (new InterviewUser())->create($params);
    }
}