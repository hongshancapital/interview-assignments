package com.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public  class Result<T> {
    /**
     * 业务错误码
     * */
    @JsonProperty("code")
    private Integer code;
    /**
     * 信息描述
     * */
    @JsonProperty("message")
    private String message;
    /**
     * 返回参数
     * */
    @JsonProperty("data")
    private T data;

    /**
     * 返回参数
     * */
    @JsonProperty("isSuccess")
    private Boolean isSuccess;

    public Boolean getIsSuccess() {
        if (getCode()==ResultStatus.SUCCESS.getCode()) {
            return true;
        } else {
            return false;
        }
    }

    public Result(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    /** 业务成功返回业务代码,描述和返回的参数 */
    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultStatus.SUCCESS, data);
    }

    /** 业务成功返回业务代码,描述和返回的参数 */
    public static <T> Result<T> success(ResultStatus resultStatus, T data) {
        if (resultStatus == null) {
            return success(data);
        }
        return new Result<T>(resultStatus, data);
    }

    /** 业务异常返回业务代码和描述信息 */
    public static <T> Result<T> failure() {
        return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR, null);
    }

    /** 业务异常返回业务代码,描述和返回的参数 */
    public static <T> Result<T> failure(ResultStatus resultStatus) {
        return failure(resultStatus, null);
    }

    /** 业务异常返回业务代码,描述和返回的参数 */
    public static <T> Result<T> failure(ResultStatus resultStatus, T data) {
        if (resultStatus == null) {
            return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR, null);
        }
        return new Result<T>(resultStatus, data);
    }

    /** 业务异常返回业务代码,描述和返回的参数 */
    public static <T> Result<T> failure(Integer code,String message) {
        return new Result<T>(code,message, null);
    }

}
