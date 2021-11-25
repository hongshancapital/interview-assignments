<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "reg_user".
 *
 * @property int $id
 * @property string $Username 用户名
 * @property string $Password 密码
 * @property string $Salt 盐
 * @property int|null $status 用户状态
 * @property int $created_at 注册日期
 * @property int $updated_at 最近一次修改日期
 */
class User extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'reg_user';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['Username', 'Password', 'Salt', 'created_at', 'updated_at'], 'required'],
            [['status', 'created_at', 'updated_at'], 'integer'],
            [['Username'], 'string', 'max' => 255],
            [['Password'], 'string', 'max' => 32],
            [['Salt'], 'string', 'max' => 6],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'Username' => 'Username',
            'Password' => 'Password',
            'Salt' => 'Salt',
            'status' => 'Status',
            'created_at' => 'Created At',
            'updated_at' => 'Updated At',
        ];
    }
}
