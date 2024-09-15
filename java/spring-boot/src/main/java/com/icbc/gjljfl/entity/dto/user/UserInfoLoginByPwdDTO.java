package com.icbc.gjljfl.entity.dto.user;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: 高一平
 * @Description: 用户账号密码登录传输对象
 **/
public class UserInfoLoginByPwdDTO {

    @NotEmpty(message = "用户名不可为空")
    private String userName;
    @NotEmpty(message = "密码不可为空")
    private String userPwd;
    @NotEmpty(message = "请选择用户/环卫工人")
    private String userType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
