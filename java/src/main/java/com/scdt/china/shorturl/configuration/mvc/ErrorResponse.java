package com.scdt.china.shorturl.configuration.mvc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 错误响应.
 *
 * @author ng-life
 */
@ApiModel("ErrorResponse")
public class ErrorResponse {

    /**
     * 错误ID.
     */
    @ApiModelProperty("错误ID")
    private String eid;

    /**
     * URL.
     */
    @ApiModelProperty("URL")
    private String url;

    /**
     * 错误编码
     */
    @ApiModelProperty("错误编码")
    private Integer errorCode;

    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    private String errorMessage;

    /**
     * 获取 错误ID.
     *
     * @return eid 错误ID.
     */
    public String getEid() {
        return this.eid;
    }

    /**
     * 设置 错误ID.
     *
     * @param eid 错误ID.
     */
    public void setEid(String eid) {
        this.eid = eid;
    }

    /**
     * 获取 错误编码
     *
     * @return errorCode 错误编码
     */
    public Integer getErrorCode() {
        return this.errorCode;
    }

    /**
     * 设置 错误编码
     *
     * @param errorCode 错误编码
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取 错误信息
     *
     * @return errorMessage 错误信息
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * 设置 错误信息
     *
     * @param errorMessage 错误信息
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 获取 URL.
     *
     * @return url URL.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * 设置 URL.
     *
     * @param url URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
