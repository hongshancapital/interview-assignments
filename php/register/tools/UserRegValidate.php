<?php
namespace app\tools;

class UserRegValidate
{
    public static $usernameReg = "/^[a-zA-Z_][a-zA-Z0-9_\-]+$/";
    public static $passwordLengthReg = "/^.{6,}$/";
    public static $passwordHasUpperReg = "/[A-Z]+/";
    public static $passwordHasLowerReg = "/[a-z]+/";
    public static $passwordHasNumberReg = "/[0-9]+/";
    public static $numberReg = "/[0-9]+/";

    public static function isUsername($name)
    {
        if (!preg_match(static::$usernameReg,$name)){
            return ['code'=>400, 'msg'=>'姓名格式错误'];
        }
        return ['code'=>200, 'msg'=>'姓名验证通过'];
    }
    public static function isPassword($pwd)
    {
        if (!preg_match(static::$passwordLengthReg,$pwd)){
            return ['code'=>400, 'msg'=>'密码长度必须大于6位'];
        }

        $j = 0;
        $_tmpCompareArray = [];
        for ($i=0;$i< strlen($pwd);$i++){
        if (!preg_match(static::$numberReg,$pwd)){
            $j = 0 ;
                $_tmpCompareArray = [];
                continue ;
            }
        $_tmpCompareArray[$j++] = $pwd[$i];
            if ($j>=3){
                if (intval($_tmpCompareArray[0])+1 == intval($_tmpCompareArray[1]) && intval($_tmpCompareArray[1])+1 == intval($_tmpCompareArray[2])){
                    return ['code'=>400, 'msg'=>'密码中不能有三个以上的连续数字'];
                }else{
                    $j = 0 ;
                    $_tmpCompareArray = [];
                    continue ;
                }
            }
        }

        if (preg_match(static::$passwordHasUpperReg,$pwd) && preg_match(static::$passwordHasLowerReg,$pwd)){
            return ['code'=>200, 'msg'=>'验证通过'];
        }
        if (preg_match(static::$passwordHasUpperReg,$pwd) && preg_match(static::$passwordHasNumberReg,$pwd)){
            return ['code'=>200, 'msg'=>'验证通过'];
        }
        if (preg_match(static::$passwordHasLowerReg,$pwd) && preg_match(static::$passwordHasNumberReg,$pwd)){
            return ['code'=>200, 'msg'=>'验证通过'];
        }
        return ['code'=>400, 'msg'=>'密码中必须有大写字母，小写字母或数字中的两项'];
    }
}