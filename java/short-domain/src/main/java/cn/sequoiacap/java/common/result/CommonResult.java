package cn.sequoiacap.java.common.result;

public class CommonResult {

    private int code;
    private String message;
    private Object data;
    private long timestamp = System.currentTimeMillis();

    public CommonResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     * @param data 获取的数据
     */
    public static CommonResult success(Object data) {
        return success(data, ResultCode.SUCCESS.getMessage());
    }

    /**
     * 成功返回结果
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static CommonResult success(Object data, String message) {
        return new CommonResult(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static CommonResult failed(ResultCode errorCode) {
        return new CommonResult(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static CommonResult failed(ResultCode errorCode, String message) {
        return new CommonResult(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 错误信息
     */
    public static CommonResult failed(String message) {
        return failed(ResultCode.INTERNAL_ERROR, message);
    }

    /**
     * 失败返回结果
     */
    public static CommonResult failed() {
        return failed(ResultCode.INTERNAL_ERROR);
    }

    /**
     * 找不到资源
     * @param message 提示信息
     */
    public static CommonResult notFound(String message) {
        return new CommonResult(ResultCode.NOT_FOUND.getCode(), message, null);
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

}
