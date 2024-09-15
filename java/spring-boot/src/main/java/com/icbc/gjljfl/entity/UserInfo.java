package com.icbc.gjljfl.entity;

import java.util.Date;

public class UserInfo {
    private Integer id;

    private String userId;

    private String userName;

    private String userPwd;

    private String userType;

    private String mobile;

    private String userSex;

    private String name;

    private String address;

    private String communityId;

    private String token;

    private Date createTime;

    private Date modifyTime;

    private String userStatusc;

    private Date lastLoginTime;

    private String loginIp;

    //冗余字段
    private String userQrStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUserStatusc() {
        return userStatusc;
    }

    public void setUserStatusc(String userStatusc) {
        this.userStatusc = userStatusc == null ? null : userStatusc.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getUserQrStatus() {
        return userQrStatus;
    }

    public void setUserQrStatus(String userQrStatus) {
        this.userQrStatus = userQrStatus;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userType='" + userType + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userSex='" + userSex + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", communityId=" + communityId +
                ", token='" + token + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", userStatusc='" + userStatusc + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", loginIp='" + loginIp + '\'' +
                ", userQrStatus='" + userQrStatus + '\'' +
                '}';
    }
}