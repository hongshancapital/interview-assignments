package com.hongshan.shorturl.model.resps;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.StringJoiner;

/**
 * @author: huachengqiang
 * @date: 2022/1/16
 * @description:
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> {
    private static final int SUCCESS = 0;
    private static final int ERROR = -1;
    /**
     * code
     */
    private int code;
    /**
     * 失败消息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    private ResultVO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static<T> ResultVO<T> success(T data){
        return new ResultVO<T>(SUCCESS, "ok", data);
    }

    public static ResultVO<String> error(int code, String message){
        return new ResultVO<String>(code, message, null);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ResultVO.class.getSimpleName() + "[", "]")
                .add("code=" + code)
                .add("message='" + message + "'")
                .add("data=" + data)
                .toString();
    }
}
