package com.github.shwas1.shorturl.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回结果
 */
@ApiModel(value = "响应体")
public class Result {
    /**
     * 是否成功
     */
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    /**
     * 失败信息
     */
    @ApiModelProperty(value = "失败信息")
    private String errorMessage;
    /**
     * 业务数据
     */
    @ApiModelProperty(value = "业务数据")
    private Object data;

    public static Result fail(Exception e) {
        Result result = new Result();
        result.setSuccess(false);
        result.setErrorMessage(e.getMessage());
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
