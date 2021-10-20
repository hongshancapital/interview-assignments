package com.assignment.vo;


import com.assignment.common.enums.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author sf
 */
@ApiModel("响应结果")
public class Result {
    @ApiModelProperty(value = "状态码")
    private String statusCode;
    @ApiModelProperty(value = "状态描述")
    private String statusMsg;
    @ApiModelProperty(value = "data")
    private Object data;

    public Result(String code, String msg) {
        this.statusCode = code;
        this.statusMsg = msg;
    }

    public Result(ErrorCode errorCode) {
        this.statusCode = errorCode.getCode();
        this.statusMsg = errorCode.getMessage();
    }

    public static Result ok() {
        return new Result(ErrorCode.SUCCESS);
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg);
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public Object getData() {
        return data;
    }
}
