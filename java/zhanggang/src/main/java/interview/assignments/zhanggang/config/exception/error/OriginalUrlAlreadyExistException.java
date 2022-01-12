package interview.assignments.zhanggang.config.exception.error;

import interview.assignments.zhanggang.config.exception.base.BizException;

public class OriginalUrlAlreadyExistException extends BizException {
    public OriginalUrlAlreadyExistException(String url) {
        super("The url: [" + url + "] already exist.");
    }
}