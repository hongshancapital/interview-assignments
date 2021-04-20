<?php

/**
 * @Author   : zhaoyinfan
 * @Date     : 2021-04-16 17:50
 * @Describe : 基类
 */
class Base
{

    public static function getParams($key, $default = null)
    {
        return isset($_GET[$key]) ? $_GET[$key] : (isset($_POST[$key]) ? $_POST[$key] : $default);
    }
}