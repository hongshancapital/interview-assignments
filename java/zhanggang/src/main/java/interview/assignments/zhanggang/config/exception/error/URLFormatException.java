package interview.assignments.zhanggang.config.exception.error;

import interview.assignments.zhanggang.config.exception.BizException;

public class URLFormatException extends BizException {
    public URLFormatException() {
        super("The short url format is invalid.");
    }
}
