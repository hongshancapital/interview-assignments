package interview.shorturl.exception;

import interview.shorturl.response.ResponseCodeEnum;

/**
 * @author vanki
 */
public class BusException extends RuntimeException {
    private final int code;
    private final String msg;
    private final Object data;

    public BusException(ResponseCodeEnum responseCodeEnum) {
        this(responseCodeEnum.getCode(), responseCodeEnum.getMsg(), null);
    }

    public BusException(ResponseCodeEnum responseCodeEnum, String msg) {
        this(responseCodeEnum.getCode(), msg, null);
    }

    public BusException(int code, String msg) {
        this(code, msg, null, null);
    }

    public BusException(int code, String msg, Object data) {
        this(code, msg, data, null);
    }

    public BusException(int code, String msg, Object data, Throwable throwable) {
        super(msg, throwable);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
