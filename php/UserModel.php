<?php

namespace App\Model;


use Illuminate\Database\Eloquent\SoftDeletes;

class UserModel extends Model
{
    /**
     * 与模型关联的表名
     *
     * @var string
     */
    protected $table = 'data_user';

    use SoftDeletes; //软删除
    /**
     * 指示模型是否自动维护时间戳
     *
     * @var bool
     */
    public $timestamps = true;
    /**
     * 模型日期列的存储格式。
     *
     * @var string
     */
    protected $dateFormat = 'U';

    /**
     * 模型的连接名称
     *
     * @var string
     */
    protected $connection = 'connection-name';

}
