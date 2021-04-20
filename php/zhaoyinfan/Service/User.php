<?php

/**
 * @Author   : zhaoyinfan
 * @Date     : 2021-04-16 17:50
 * @Describe : 用户信息验证类
 */
class User
{
    /**
     * Method checkUserName
     * 只能以英文字母或下划线开头
     * 只能包含英文字母，下划线或数字
     *
     * @param $userName
     *
     * @return array
     * @author  zhaoyinfan
     */
    public static function checkUserName($userName)
    {
        $preg = '/^[A-Za-z_][\w]+/i';
        if (preg_match($preg, $userName)) {
            return [
                'code'    => 1000,
                'message' => 'success',
            ];
        }

        return [
            'code'    => 1001,
            'message' => '用户名：以英文字母或下划线开头，只能包含英文字母，下划线或数字',
        ];
    }

    /**
     * Method checkPassword
     * 长度在 6 位以上
     * 不能含有 3 位以上的连续数字
     * 必须有大写字母，小写字母或数字中的两项
     *
     * @param $password
     *
     * @return array
     * @author  zhaoyinfan
     */
    public static function checkPassword($password)
    {
        if (strlen($password) <= 6) {
            return [
                'code'    => 1001,
                'message' => '密码长度需在 6 位以上',
            ];
        }
        $preg = '/\d{3}/';
        preg_match_all($preg, $password, $match);
        $result = $match[0] ?? [];
        if ($result) {
            return [
                'code'    => 1002,
                'message' => '密码不能含有 3 位以上的连续数字',
            ];
        }

        $pregArr    = [
            '/[A-Z]+/',
            '/[a-z]+/',
            '/[0-9]+/',
        ];
        $caseNumber = 0;
        foreach ($pregArr as $key => $value) {
            if (preg_match($value, $password)) {
                $caseNumber++;
            }
        }
        if ($caseNumber > 1) {
            return [
                'code'    => 1000,
                'message' => 'success',
            ];
        }

        return [
            'code'    => 1003,
            'message' => '密码必须有大写字母，小写字母或数字中的两项',
        ];
    }

    /**
     * Method checkRepeatPassword
     *
     * @param $password
     * @param $repeatPassword
     *
     * @return array
     * @author  zhaoyinfan
     */
    public static function checkRepeatPassword($password, $repeatPassword)
    {
        if ($password && $password === $repeatPassword) {
            return [
                'code'    => 1000,
                'message' => 'success',
            ];
        }

        return [
            'code'    => 1001,
            'message' => '两次密码不一致',
        ];
    }

    public static function saveUser($userName, $password)
    {

    }
}