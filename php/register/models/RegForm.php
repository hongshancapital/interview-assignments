<?php

namespace app\models;

use Yii;
use yii\base\Model;

/**
 * LoginForm is the model behind the login form.
 *
 * @property-read User|null $user This property is read-only.
 *
 */
class RegForm extends Model
{
    public $Username;
    public $Password;
    public $RepeatPassword;

    private $_user = false;


    /**
     * @return array the validation rules.
     */
    public function rules()
    {
        return [
            [['Username', 'Password','RepeatPassword'], 'required'],
            ['Password', 'validatePassword'],
        ];
    }

    /**
     * Validates the Password.
     * This method serves as the inline validation for Password.
     *
     * @param string $attribute the attribute currently being validated
     * @param array $params the additional name-value pairs given in the rule
     */
    public function validatePassword($attribute, $params)
    {
        if (!$this->hasErrors()) {
            $user = $this->getUser();

            if (!$user || !$user->validatePassword($this->Password)) {
                $this->addError($attribute, 'Incorrect Username or Password.');
            }
        }
    }

    /**
     * Logs in a user using the provided Username and Password.
     * @return bool whether the user is logged in successfully
     */
    public function login()
    {
        if ($this->validate()) {
            return Yii::$app->user->login($this->getUser(), $this->rememberMe ? 3600*24*30 : 0);
        }
        return false;
    }

    /**
     * Finds user by [[Username]]
     *
     * @return User|null
     */
    public function getUser()
    {
        if ($this->_user === false) {
            $this->_user = User::findByUsername($this->Username);
        }

        return $this->_user;
    }
}
