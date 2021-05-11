<?php

namespace app\models;

class User extends \yii\db\ActiveRecord
{

    public static function tableName()
    {
        return '{{user}}';
    }
    /**
     * @return array the validation rules.
     */
    public function rules()
    {
        return [
            // username and password are both required
            [['username', 'password'], 'required'],
            [['username'], 'match','pattern'=>'/^[A-Za-z\-_]\w[A-Za-z0-9_]+$/','message'=>'用户名只能以英文字母或下划线开头,只能包含英文字母，下划线或数字'],
            //[['password'], 'match','pattern'=>'/^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[\(\)])+$)([^(0-9a-zA-Z)]|[\(\)]|[a-z]|[A-Z]|[0-9]){6,}$/','message'=>'密码必须有大写字母，小写字母或数字中的两项,并且长度在 6 位以上'],
//            [['password'], 'match','pattern'=>'/(^012|123|234|345|456|567|678|789)$/','message'=>'密码不能含有 3 位以上的连续数字'],

            [['password'], 'validatePassword','message'=>'密码必须有大写字母，小写字母或数字中的两项,并且长度在 6 位以上,并且密码不能含有 3 位以上的连续数字'],
        ];
    }

    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'username' => 'username',
            'password' => 'password',
        ];
    }

    /**
     * {@inheritdoc}
     */
    public static function findIdentity($id)
    {
        return isset(self::$users[$id]) ? new static(self::$users[$id]) : null;
    }

    /**
     * {@inheritdoc}
     */
    public static function findIdentityByAccessToken($token, $type = null)
    {
        foreach (self::$users as $user) {
            if ($user['accessToken'] === $token) {
                return new static($user);
            }
        }

        return null;
    }

    /**
     * Finds user by username
     *
     * @param string $username
     * @return static|null
     */
    public static function findByUsername($username)
    {
        foreach (self::$users as $user) {
            if (strcasecmp($user['username'], $username) === 0) {
                return new static($user);
            }
        }

        return null;
    }

    /**
     * {@inheritdoc}
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * {@inheritdoc}
     */
    public function getAuthKey()
    {
        return $this->authKey;
    }

    /**
     * {@inheritdoc}
     */
    public function validateAuthKey($authKey)
    {
        return $this->authKey === $authKey;
    }

    /**
     * Validates password
     *
     * @param string $password password to validate
     * @return bool if password provided is valid for current user
     */
    public function validatePassword($password)
    {
        $rep = "/^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[\(\)])+$)([^(0-9a-zA-Z)]|[\(\)]|[a-z]|[A-Z]|[0-9]){6,}$/";
        $rep1 = "/(012|123|234|345|456|567|678|789)/";
        preg_match($rep ,$this->password ,$res);
        if(!$res){
            $this->addError('password', "密码必须有大写字母，小写字母或数字中的两项,并且长度在 6 位以上.");

        }
        preg_match($rep1 ,$this->password ,$res1);
        if($res1){
            $this->addError('password', "密码不能含有 3 位以上的连续数字.");
        }

        return $this->password === $password;
    }
}
