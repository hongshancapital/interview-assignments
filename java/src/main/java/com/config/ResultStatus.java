package com.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@Getter
@ToString
public enum ResultStatus {

    SUCCESS(HttpStatus.OK, 200, "OK"),
    FAILURE(HttpStatus.BAD_REQUEST, 1000, "失败"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE,100001,"服务暂不可用"),
    LONG_URL_NOT_FOUND(HttpStatus.BAD_REQUEST,100002,"不存在相应的长链接"),
    GENERATE_REPEAT(HttpStatus.BAD_REQUEST, 100002, "生成短链接错误"),
    EXECUTE_FAIL(HttpStatus.BAD_REQUEST, 503, "执行失败");

    /**
     * 返回的HTTP状态码,  符合http请求
     */
    private HttpStatus httpStatus;
    /**
     * 业务异常码
     */
    private Integer code;
    /**
     * 业务异常信息描述
     */
    private String message;

    ResultStatus(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
