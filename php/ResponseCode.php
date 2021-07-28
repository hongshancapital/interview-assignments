<?php
namespace App\Common;

/**
 * 返回码定义
 */
class ResponseCode{
    // 成功
    const SUCCESS = 1;
    const SYS_ERROR = 0;


    const USERNAME_ERROR = 2001; //用户名不合规
    const PASSWORD_ERROR = 2002; //密码不合规

}
