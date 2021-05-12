<?php

namespace app\module;

use app\common\mysql;
use think\facade\Db;

class User
{
    public function __construct()
    {
        new mysql();
    }

    public function addUser($data)
    {
        return Db::name('user')->insert($data);
    }
}
