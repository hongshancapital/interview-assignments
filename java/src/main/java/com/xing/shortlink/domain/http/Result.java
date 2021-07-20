package com.xing.shortlink.domain.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一应答封装
 *
 * @Author xingzhe
 * @Date 2021/7/17 22:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    /**
     * 默认成功返回code
     */
    private static Integer successCode = 0;

    /**
     * 默认成功返回message
     */
    private static String successMessage = "success";

    /**
     * 请求返回码
     */
    private Integer code;

    /**
     * 请求返回信息
     */
    private String message;

    /**
     * 请求返回实体
     */
    private T data;

    /**
     * 默认成功返回
     *
     * @param data 数据实体
     * @param <T>  实体类型
     * @return 返回结果
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(successCode);
        result.setMessage(successMessage);
        result.setData(data);
        return result;
    }

    /**
     * 错误码和异常信息失败返回
     *
     * @param message 异常信息
     * @param <T>     实体类型
     * @return 返回结果
     */
    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
