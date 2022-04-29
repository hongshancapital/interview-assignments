package interview.shorturl.response;


/**
 * 接口响应数据
 *
 * @author ZOUFANQI
 */
public class ResponseData {
    private final Integer code;
    private final String msg;
    private final Object data;

    public ResponseData(ResponseCodeEnum responseCodeEnum) {
        this(responseCodeEnum.getCode(), responseCodeEnum.getMsg(), null);
    }

    public ResponseData(ResponseCodeEnum responseCodeEnum, String msg) {
        this(responseCodeEnum.getCode(), msg, null);
    }

    public ResponseData(ResponseCodeEnum responseCodeEnum, Object data) {
        this(responseCodeEnum.getCode(), responseCodeEnum.getMsg(), data);
    }

    public ResponseData(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}