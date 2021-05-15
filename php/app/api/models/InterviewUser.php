<?php
declare (strict_types=1);

/**
 * 开发: Atom
 * 时间: 2021-05-15
 */

namespace app\api\models;


use think\Model;

/**
 * 面试题用户表model
 * Class InterviewUser
 * @package app\api\models
 */
class InterviewUser extends Model
{
    /**
     * 表名
     * @var string
     */
    protected $table = 'interview_user';

    /**
     * 默认主键ID
     * @var string
     */
    protected $pk = 'id';

    /**
     * 允许写入的字段
     * @var array
     */
    protected $field = ['name','password'];
}