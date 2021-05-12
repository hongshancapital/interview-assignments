<?php
/**
 * User: xg
 * Date: 2021/5/11
 */

namespace app\common\response;

/**
 * api 返回码、文案
 * Class StatusCode
 * @package app\common\response
 */
class StatusCode
{
    public static $success       = ['code' => '1000', 'msg' => 'success'];
    public static $error         = ['code' => '9999', 'msg' => 'internal server error'];
    public static $invalid_param = ['code' => '9000', 'msg' => '参数错误'];

    // 用户注册返回码
    public static $username_invalid_begin    = ['code' => '2001', 'msg' => '用户名只能以英文字母或下划线开头'];
    public static $username_invalid_include  = ['code' => '2002', 'msg' => '用户名只能包含英文字母，下划线或数字'];
    public static $username_invalid_exist    = ['code' => '2003', 'msg' => '用户名只已注册'];
    public static $password_invalid_length   = ['code' => '2004', 'msg' => '密码长度在 6 位以上'];
    public static $password_invalid_mixed    = ['code' => '2005', 'msg' => '密码必须有大写字母，小写字母或数字中的两项'];
    public static $password_invalid_confirm  = ['code' => '2006', 'msg' => '请再次确认密码'];
    public static $password_invalid_consec   = ['code' => '2007', 'msg' => '密码不能含有 3 位以上的连续数字'];


}