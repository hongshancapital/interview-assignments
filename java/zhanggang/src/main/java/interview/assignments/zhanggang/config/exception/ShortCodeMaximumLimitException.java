package interview.assignments.zhanggang.config.exception;

public class ShortCodeMaximumLimitException extends RuntimeException {
    public ShortCodeMaximumLimitException(long seed) {
        super(seed + " has reached the maximum length limit.");
    }
}
