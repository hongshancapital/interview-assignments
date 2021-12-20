package com.example.baiyang.demo.model;

import lombok.Data;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/16
 * @description:返回结果类
 */
@Data
public final class Result {
    private Boolean success = true;
    private ResponseDTO response;
    private String errorMessage;
    private String errorCode;

    /**
     * 构建成功返回结果
     *
     * @param response
     * @return
     */
    public static Result newSuccessResult(ResponseDTO response) {
        Result result = new Result();
        result.success = true;
        result.setResponse(response);
        return result;
    }

    /**
     * 构建失败返回结果
     *
     * @param errorMessage
     * @param errorCode
     * @return
     */
    public static Result newErrorResult(String errorCode, String errorMessage) {
        Result result = new Result();
        result.success = false;
        result.setErrorMessage(errorMessage);
        result.setErrorCode(errorCode);
        return result;
    }
}
