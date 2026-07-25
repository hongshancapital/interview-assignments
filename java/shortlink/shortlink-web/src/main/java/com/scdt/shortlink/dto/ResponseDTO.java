package com.scdt.shortlink.dto;

import com.scdt.shortlink.client.domain.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * controller层返回值对象
 *
 * @Author tzf
 * @Date 2022年05月03日 05:01
 */
@ApiModel(description = "返回值对象")
@Data
public class ResponseDTO<T> implements Serializable {
    private static final long serialVersionUID = 7189953941309962809L;

    /**
     * 数据实体
     */
    @ApiModelProperty(value="接口返回数据")
    private T model;
    /**
     * 调用是否成功
     */
    @ApiModelProperty(value="接口调用是否成功")
    private Boolean isSuccess;
    /**
     * 接口结果
     */
    @ApiModelProperty(value="接口结果")
    private Boolean result;
    /**
     * 错误信息
     */
    @ApiModelProperty(value="错误信息")
    private String errorMessage;
    /**
     * 错误码
     */
    @ApiModelProperty(value="错误码")
    private String errorCode;

    /**
     * 成功返回
     *
     * @return
     */
    public static ResponseDTO success() {
        ResponseDTO thisResult = new ResponseDTO();
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
    public static ResponseDTO error(String errorCode, String errorMessage) {
        ResponseDTO thisResult = new ResponseDTO();
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
    public static ResponseDTO error(ErrorCode errorCode) {
        ResponseDTO thisResult = new ResponseDTO();
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
    public static <T> ResponseDTO<T> success(T model) {
        ResponseDTO thisResult = new ResponseDTO();
        thisResult.setResult(true);
        thisResult.setIsSuccess(true);
        thisResult.setModel(model);
        return thisResult;
    }
}