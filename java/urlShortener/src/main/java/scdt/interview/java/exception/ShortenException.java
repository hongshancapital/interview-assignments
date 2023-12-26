package scdt.interview.java.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 短链接自定义异常
 * @author Laerfu Zhang
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class ShortenException extends RuntimeException {

    /**
     * HTTP 状态码
     */
    private int code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 构造函数
     * @param code HTTP 状态码
     * @param message 错误消息
     */
    public ShortenException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
