package cn.sequoiacap.java.common.result;

import cn.sequoiacap.java.common.def.Constants;

public enum ResultCode {
    /**
     * 处理成功
     */
    SUCCESS(200, Constants.SUCCESS_MSG),

    /**
     * 找不到资源
     */
    NOT_FOUND(404, Constants.NOT_FOUND_MSG),

    /**
     * 内部错误
     */
    INTERNAL_ERROR(500, Constants.INTERNAL_ERROR_MSG);


    private int code;
    private String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
