package com.francis.urltransfer.common;

import com.francis.urltransfer.exception.ServerException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Francis
 * @Description: 响应结果实体类
 * @date 2022/1/27 0:11
 */
@Schema(name = "响应结果")
@Data
@EqualsAndHashCode
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

    public static Result<Object> of(ServerException exception) {
        return new Result<Object>(exception.getCode(), exception.getMessage());
    }

    public static Result<Object> ok() {
        return new Result<Object>(200, "success");
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>(200, "success", data);
    }

    public static Result<Object> fail(int code, String message) {
        return new Result<Object>(code, message);
    }

    public static <T> Result<T> warning(T data) {
        return new Result<T>(10000, "warning", data);
    }

}
