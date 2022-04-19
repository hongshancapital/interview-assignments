package com.tizzy.business.response;

public class ResponseMessage {

    public static final Integer SUCCESS_STATUS = 200;
    public static final Integer ERROR_STATUS = 201;

    private String shortUrl;

    private String originUrl;

    private Boolean success;

    private String message;

    private Integer code;

    public ResponseMessage(String shortUrl, String originUrl, Boolean success, String message) {
        this.shortUrl = shortUrl;
        this.originUrl = originUrl;
        this.success = success;
        this.message = message;
        this.code = success ? SUCCESS_STATUS : ERROR_STATUS;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
