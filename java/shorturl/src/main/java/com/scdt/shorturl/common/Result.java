package com.scdt.shorturl.common;

import com.scdt.shorturl.exception.ServerException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author leo
 * @Description: 响应结果实体类
 * @date 2022/2/26 11:11
 */
@Schema(name = "响应结果")
@Getter
@Setter
public class Result<T> {

    @Schema(description = "响应码")
    private int code;

    @Schema(description = "响应信息")
    private String message;

    @Schema(description = "数据结果")
    private T data;

    public Result(int code, String message) {
        setCode(code);
        setMessage(message);
    }

    public Result(int code, String message, T data) {
        setCode(code);
        setMessage(message);
        setData(data);
    }
    public static <T> Result<T> ok(T data) {
        return new Result<T>(200, "success", data);
    }

    public static Result<Object> fail( String message) {
        return new Result<Object>(500, message);
    }
}
