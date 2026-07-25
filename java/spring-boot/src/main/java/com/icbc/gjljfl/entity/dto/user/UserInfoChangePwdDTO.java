package com.icbc.gjljfl.entity.dto.user;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: 高一平
 * @Description: 用户修改密码传输对象
 **/
public class UserInfoChangePwdDTO {

    @NotEmpty(message = "用户ID不可为空")
    private String userId;
    @NotEmpty(message = "旧密码不可为空")
    private String userOldPwd;
    @NotEmpty(message = "新密码不可为空")
    private String userNewPwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserOldPwd() {
        return userOldPwd;
    }

    public void setUserOldPwd(String userOldPwd) {
        this.userOldPwd = userOldPwd;
    }

    public String getUserNewPwd() {
        return userNewPwd;
    }

    public void setUserNewPwd(String userNewPwd) {
        this.userNewPwd = userNewPwd;
    }
}
