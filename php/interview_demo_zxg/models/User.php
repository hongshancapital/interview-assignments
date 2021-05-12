<?php

namespace app\models;

use yii\db\ActiveRecord;

/**
 * Class User
 * @package app\models
 * @property int $id
 * @property string|null $username
 * @property string|null $password
 * @property string|null $created_date
 * @property string|null $updated_date
 */
class User extends ActiveRecord
{
    public static function tableName()
    {
        return 'tb_user';
    }

    public function rules()
    {
        return [
            // username、password、repeat_password required
            [['username', 'password'], 'required'],
            [['id'], 'integer'],
            [['username','password'], 'string'],
            [['created_date','updated_date'], 'string'],
        ];
    }
    public function attributes() {
        return [
            'id',
            'username',
            'password',
            'created_date',
            'updated_date',
        ];
    }

    public static function getUserByUsername($username){
        return self::find()->where(['username'=>$username])->one();
    }

    /**
     * 密码加密
     */
    public function generatePassword(){
        $salt = "php";
        $this->password = md5($this->password . $salt);
    }
}
