package com.scdt.exam.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 短连接实体
 */
public class ShortUrl implements Serializable {
    /**
     * 主键
     *
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 长连接
     *
     */
    @ApiModelProperty(value = "长连接")
    private String fullUrl;

    /**
     * 短链接码
     *
     */
    @ApiModelProperty(value = "短链接码")
    private String code;

    /**
     * 创建时间
     *
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 表名
     */
    private String tableName;

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     *
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 长连接
     *
     */
    public String getFullUrl() {
        return fullUrl;
    }

    /**
     * 长连接
     *
     */
    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    /**
     * 短链接码
     *
     */
    public String getCode() {
        return code;
    }

    /**
     * 短链接码
     *
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 创建时间
     *
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     *
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "ShortUrl{" +
                "id=" + id +
                ", fullUrl='" + fullUrl + '\'' +
                ", code='" + code + '\'' +
                ", createTime=" + createTime +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}