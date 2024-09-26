package url.converter.exception;

import lombok.Data;
import url.converter.common.constant.ErrorCode;

/**
 * 请求参数校验异常
 */
@Data
public class BadRequestParamException extends RuntimeException {

    private int code = -1;

    private String msg;

    public BadRequestParamException(Throwable cause) {
        super(cause);
    }

    public BadRequestParamException(String message) {
        super(message);
        this.msg = message;
    }

    public BadRequestParamException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BadRequestParamException(ErrorCode bizCode) {
        super(bizCode.errorMsg());
        this.code = bizCode.code();
        this.msg = bizCode.errorMsg();
    }
}
