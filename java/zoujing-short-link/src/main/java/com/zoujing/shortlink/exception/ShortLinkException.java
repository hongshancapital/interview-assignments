package com.zoujing.shortlink.exception;

public class ShortLinkException extends RuntimeException {
    private static final long serialVersionUID = 4809116393685891802L;

    /**
     * 错误枚举
     */
    private ShortLinkResultEnum resultCode;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 通过文本和Exception构造
     */
    public ShortLinkException(ShortLinkResultEnum resultEnum) {
        super(resultEnum.getCode() + ":" + resultEnum.getDesc());
        this.resultCode = resultEnum;
    }

    public ShortLinkResultEnum getResultCode() {
        return resultCode;
    }

    public void setResultCode(ShortLinkResultEnum resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
