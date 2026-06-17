package interview.assignments.zhanggang.config.exception.error;

import interview.assignments.zhanggang.config.exception.BizException;

public class ShortIdMaximumLimitException extends BizException {
    public ShortIdMaximumLimitException() {
        super("The short ID has reached the maximum length limit.");
    }
}
