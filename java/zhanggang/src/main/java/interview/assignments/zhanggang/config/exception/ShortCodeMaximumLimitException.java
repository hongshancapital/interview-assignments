package interview.assignments.zhanggang.config.exception;

public class ShortCodeMaximumLimitException extends RuntimeException {
    public ShortCodeMaximumLimitException(String code) {
        super(code + " has reached the maximum length limit.");
    }
}
