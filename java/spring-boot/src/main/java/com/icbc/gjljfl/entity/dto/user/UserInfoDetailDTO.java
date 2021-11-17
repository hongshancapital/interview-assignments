package com.icbc.gjljfl.entity.dto.user;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: 高一平
 * @Description: 用户信息传输对象
 **/
public class UserInfoDetailDTO {

    @NotEmpty(message = "用户id不可为空")
    private String userId;
    private String userSex;
    private String name;
    private String address;
    private Integer communityId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

}
