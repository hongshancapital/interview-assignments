package interview.assignments.zhanggang.config.exception.error;

import interview.assignments.zhanggang.config.exception.base.BizException;

public class LockTimeoutException extends BizException {
    public LockTimeoutException() {
        super("The process has been locked by another operation, please try again later.");
    }
}
