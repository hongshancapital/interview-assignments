package com.scdt.shorturl.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author leo
 * @Description: 服务异常类
 * @date 2022/2/26 10:22
 */
@Schema(name = "服务异常")
public class ServerException extends RuntimeException {

    @Schema(name = "异常码")
    @Getter
    @Setter
    private int code;

    public ServerException(int code, String message) {
        super(message);
        setCode(code);
    }
}
