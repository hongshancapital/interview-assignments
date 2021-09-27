<?php

use App\Model\YUser;

class UserController
{
    public function register()
    {
        $username = $_POST['username'];
        $password = $_POST['password'];
        $repeatPassword = $_POST['repeat_password'];

        $validated = $this->registerValid($username, $password, $repeatPassword);
        if (!$validated) {
            return $validated;
        }

        $userModel = new User();
        $userModel->username = $username;
        $userModel->password = $password;
        $userModel->access_token = uniqid("login_");
        $user = $userModel->save();

        if ($user) {
            return $this->ReturnSuccess(200, "注册成功", $user->toArray());
        }

        return $this->ReturnError(502, "服务内部错误");
    }

    /**
     * 用户注册前置验证逻辑
     * @param $username
     * @param $password
     * @param $repeatPassword
     * @return bool|string
     */
    private function registerValid($username, $password, $repeatPassword)
    {
        if (!(ctype_alpha($username[0]) && '_' == $username[0])) {
            //必须以字母或下划线开头
            return $this->ReturnError(500, "必须以字母或下划线开头");
        }
        $tmpUsername = str_replace('_', '', $username);
        if (!ctype_alpha($tmpUsername)) {
            //只能包含英文字母，下划线或数字
            return $this->ReturnError(500, "只能包含英文字母，下划线或数字");
        }
        if ($password != $repeatPassword) {
            //两次密码输入不一致
            return $this->ReturnError(500, "两次密码输入不一致");
        }
        if (strlen($password) < 6) {
            //长度在 6 位以上
            return $this->ReturnError(500, "长度在 6 位以上");
        }
        if ($this->isSerialNumber($password)) {
            //存在3位以上连续数字
            return $this->ReturnError(500, "存在3位以上连续数字");
        }
        if (!$this->numberLowercaseUppercase($password)) {
            //必须有大写字母，小写字母或数字中的两项
            return $this->ReturnError(500, "必须有大写字母，小写字母或数字中的两项");
        }

        return true;
    }

    //字符串连续数字验证
    private function isSerialNumber(string $str): bool
    {
        for ($i = 0; $i < strlen($str); $i++) {
            if ($i == strlen($str) - 2) {
                break;
            }

            if (($str[$i] + 1 == $str[$i + 1]) && ($str[$i] + 2 == $str[$i + 2])) {
                return true;
            }
        }

        return false;
    }

    //字符串大写小写、数字必须包含其中两项验证
    private function numberLowercaseUppercase($str): bool
    {
        $isLower = false;
        $isUpper = false;
        $isNumber = false;

        for ($i = 0; $i < strlen($str); $i++) {
            if (ord($str[$i]) >= ord('A') && ord($str[$i]) <= ord("Z")) {
                $isUpper = true;
            }
            if (ord($str[$i]) >= ord('a') && ord($str[$i]) <= ord('z')) {
                $isLower = true;
            }
            if (is_numeric($isNumber)) {
                $isNumber = true;
            }
        }

        if (($isLower && $isUpper) || ($isLower && $isNumber) || ($isUpper && $isNumber)) {
            return true;
        }

        return false;
    }

    private function ReturnError($code, $message)
    {
        return json_encode([
            'code' => $code,
            'message' => $message
        ]);
    }

    private function ReturnSuccess($code, $message, $data)
    {
        return json_encode([
            'code' => $code,
            'message' => $message,
            'items' => $data
        ]);
    }
}