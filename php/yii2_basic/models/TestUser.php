<?php

namespace app\models;

use Yii;
use yii\db\ActiveRecord;
use yii\web\IdentityInterface;

// class TestUser extends ActiveRecord implements IdentityInterface
// class TestUser extends \yii\base\BaseObject implements \yii\web\IdentityInterface
class TestUser extends ActiveRecord
{
    
    public $id;
    public $username;
    public $password;
    public $repassword;
    public $created_at;
    public $updated_at;
    
    public $usernameRegexp = '/^[a-zA-Z_]([a-zA-Z0-9_]+)?$/';
    public $passwordReg1 = '/^.*(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])\w{6,}/';
    public $passwordReg2 = '/(123|234|345|456|567|678|789|012)/';

    /**
     * Table Name
     * @return string
     */
    public static function tableName()
    {
        return '{{%test_user}}';
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
    public function getId()
    {
        return $this->id;
    }

    /**
     * {@inheritdoc}
     */
    public function validateAuthKey($authKey)
    {
        return $this->authKey === $authKey;
    }
    
    /**
     * This method is used to register new user account. If Module::enableConfirmation is set true, this method
     * will generate new confirmation token and use mailer to send it to the user.
     *
     * @return bool
     */
    public function register()
    {
        try {
            $this->setAttribute('username', $this->username);
            $this->setAttribute('password', $this->password);
            $this->setAttribute('created_at', time());
            $this->setAttribute('updated_at', time());

            if (!$this->save()) {
                return false;
            }
            return true;
        } catch (\Exception $e) {
            \Yii::warning($e->getMessage());
            throw $e;
        }
    }
}
