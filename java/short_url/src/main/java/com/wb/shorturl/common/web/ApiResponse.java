package com.wb.shorturl.common.web;

import com.wb.shorturl.common.exception.ErrorCode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author bing.wang
 * @date 2021/1/8
 */

@Getter
@Setter
@Accessors(chain = true)
public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }


    /**
     * API调用ok
     * @param msg the respnse message
     * @param <T>  the type parameter
     * @return the api response
     */
    public static <T> ApiResponse<T> ok(String msg) {
        return new ApiResponse<T>().setStatus(200).setMessage(msg);
    }

    /**
     * API调用ok
     * @param msg the respnse message
     * @param <T>  the type parameter
     * @param data the data
     * @return the api response
     */
    public static <T> ApiResponse<T> ok(String msg,T data) {
        return new ApiResponse<T>().setStatus(200).setMessage(msg).setData(data);
    }

    /**
     * Prompt api response.
     *
     * @param errorCode the error code
     * @return the api response
     */
    public static ApiResponse prompt(ErrorCode errorCode) {
        return new ApiResponse().setStatus(errorCode.getCode()).setMessage(errorCode.getMessage());
    }
}
