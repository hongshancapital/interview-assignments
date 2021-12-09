package com.icbc.gjljfl.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OperationRecord {
    private Integer id;

    private String operate;

    private String userType;

    private String userId;

    private String qrCode;

    private Integer goodsId;

    private String notes;

    private BigDecimal score;

    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate == null ? null : operate.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OperationRecord{" +
                "id=" + id +
                ", operate='" + operate + '\'' +
                ", userType='" + userType + '\'' +
                ", userId='" + userId + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", goodsId=" + goodsId +
                ", notes='" + notes + '\'' +
                ", score=" + score +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}