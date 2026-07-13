package com.francis.urltransfer.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Francis
 * @Description: 服务异常类
 * @date 2022/1/27 16:19
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
