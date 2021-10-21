package ligq.url.exception;

/**
 * 重复的原始URL异常
 * @author ligq
 * @since 2021-10-21
 */
public class RepeatUrlException extends RuntimeException {
    public RepeatUrlException(String originUrl) {
        super("This Url is Repeat! OriginUrl: " + originUrl);
    }
}
