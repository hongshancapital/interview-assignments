package com.scdt.shortlink.client.dto;

import com.scdt.shortlink.client.domain.ErrorCode;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回数据统一包装
 *
 * @Author tzf
 * @Date 2022/4/28
 */
@Data
@ToString
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -8901560392449684323L;

    /**
     * 数据实体
     */
    private T model;
    /**
     * 调用是否成功
     */
    private Boolean isSuccess;
    /**
     * 接口结果
     */
    private Boolean result;
    /**
     * 错误信息
     */
    private String errorMessage;
    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 成功返回
     *
     * @return
     */
    public static Result success() {
        Result thisResult = new Result();
        thisResult.setIsSuccess(true);
        thisResult.setResult(true);
        return thisResult;
    }

    /**
     * 错误返回
     *
     * @param errorCode
     * @param errorMessage
     * @return
     */
    public static Result error(String errorCode, String errorMessage) {
        Result thisResult = new Result();
        thisResult.setResult(false);
        thisResult.setIsSuccess(true);
        thisResult.setErrorCode(errorCode);
        thisResult.setErrorMessage(errorMessage);
        return thisResult;
    }

    /**
     * 错误返回
     *
     * @param errorCode
     * @return
     */
    public static Result error(ErrorCode errorCode) {
        Result thisResult = new Result();
        thisResult.setResult(false);
        thisResult.setIsSuccess(true);
        thisResult.setErrorCode(errorCode.getErrorCode());
        thisResult.setErrorMessage(errorCode.getErrorMessgae());
        return thisResult;
    }

    /**
     * 成功范湖
     *
     * @param model
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T model) {
        Result thisResult = new Result();
        thisResult.setResult(true);
        thisResult.setIsSuccess(true);
        thisResult.setModel(model);
        return thisResult;
    }
}
