package com.icbc.gjljfl.entity.dto.user;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: 高一平
 * @Description: 用户信息传输对象
 **/
public class UserInfoRegisterDTO {

    @NotEmpty(message = "用户名不可为空")
    private String userName;
    @NotEmpty(message = "密码不可为空")
    private String userPwd;
    @NotEmpty(message = "手机号码不可为空")
    private String mobile;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
