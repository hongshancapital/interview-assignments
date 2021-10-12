package com.moonciki.interview.vo.base;

import lombok.Data;

@Data
public class LoginUserInfo {
    /** 登录类型 **/

    /** 手机 **/
    private String account;

    /** 密码 **/
    private String password;

    /** 验证码 **/
    private String validateCode;

}