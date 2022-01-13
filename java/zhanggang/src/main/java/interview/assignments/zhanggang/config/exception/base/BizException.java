package interview.assignments.zhanggang.config.exception.base;

public class BizException extends RuntimeException {
    public BizException(String msg) {
        super(msg);
    }

    public BizException(Throwable e) {
        super(e);
    }
}
