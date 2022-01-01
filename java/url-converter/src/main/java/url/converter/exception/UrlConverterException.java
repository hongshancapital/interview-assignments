package url.converter.exception;

import lombok.Data;
import url.converter.common.constant.ErrorCode;

@Data
public class UrlConverterException extends RuntimeException {

    //错误码
    private int code;
    //错误详情
    private String msg;

    public UrlConverterException(String msg) {
        super(msg);
        this.code = -1;
        this.msg = msg;
    }

    public UrlConverterException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public UrlConverterException(ErrorCode bizCode) {
        super(bizCode.errorMsg());
        this.code = bizCode.code();
        this.msg = bizCode.errorMsg();
    }

    public UrlConverterException(ErrorCode bizCode, String msg) {
        super(msg);
        this.code = bizCode.code();
        this.msg = msg;
    }

    public UrlConverterException(ErrorCode bizCode, Throwable e) {
        super(bizCode.errorMsg(), e);
        this.code = bizCode.code();
        this.msg = bizCode.errorMsg();
    }

    public UrlConverterException(ErrorCode bizCode, String msg, Throwable e) {
        super(msg, e);
        this.code = bizCode.code();
        this.msg = msg;
    }

}
