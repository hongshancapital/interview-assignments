package interview.shorturl.response;

/**
 * 接口响应状态
 *
 * @author ZOUFANQI
 */
public enum ResponseCodeEnum {
    /**
     * 公共
     */
    SUCCESS(200, "success"),
    PARAM_ERROR(400, "params error"),
    HTTP_METHOD_NOT_SUPPORTED(401, "http method not support"),
    NOT_FOUND(404, "resource not found"),
    EXPIRED(405, "resource expired"),
    FAIL(444, "something wrong happened");

    private final int code;
    private final String msg;

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}