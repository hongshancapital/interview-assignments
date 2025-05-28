<?php

namespace app\models;

use Yii;
use yii\base\Model;

/**
 * TestRegisterForm is the model behind the register form.
 *
 * @property-read User|null $user This property is read-only.
 *
 */
class TestRegisterForm extends Model
{
    public $username;
    public $password;
    public $repassword;
    public $created_at;
    public $updated_at;
    
    private $_user = false;


    /**
     * @return array the validation rules.
     */
    public function rules()
    {
        $user = new TestUser();
        
        return [
            // username rules
            'usernameTrim'     => ['username', 'trim'],
            'usernameLength'   => ['username', 'string', 'min' => 3, 'max' => 255],
            'usernamePattern'  => ['username', 'match', 'pattern' => $user->usernameRegexp],
            'usernameRequired' => ['username', 'required'],
            'usernameUnique'   => [
                'username',
                'unique',
                'targetClass' => $user,
                'message' => Yii::t('app', 'This username has already been taken 1')
            ],
            // password rules
            'passwordRequired' => ['password', 'required'],
            'passwordPattern1'  => ['password', 'match', 'pattern' => $user->passwordReg1, 'message' => yii::t('app', '1 Must have two of uppercase letters, lowercase letters or numbers And Length > 6')],
            'passwordPattern2'  => ['password', 'match', 'pattern' => $user->passwordReg2, 'not' => true, 'message' => yii::t('app', '1 Cannot contain 3 or more consecutive numbers, such as 123, 234, etc.')],
            // Repasswod rules
            'repasswordRequired' => ['repassword', 'required'],
            'repasswordCompare' => ['repassword', 'compare', 'compareAttribute'=>'password'],
        ];
    }


    /**
     * Logs in a user using the provided username and password.
     * @return bool whether the user is logged in successfully
     */
    public function register()
    {
        if ($this->validate()) {
            $user = new TestUser();
            $user->username = $this->username;
            $user->password = $this->password;
            if ($user->register()) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }
    
}
