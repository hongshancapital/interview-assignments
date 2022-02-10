package org.example.sgenerate.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 接口响应结果
 * @author admin
 */
@ApiModel(value = "接口响应结果")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = -6190689122701100762L;

    public static final int OK = 200;
    public static final int FAILED = -1;
    /**
     * 编码
     */
    @ApiModelProperty(value = "编码:200-请求处理成功")
    private int code = 200;

    /**
     * 提示消息
     */
    @ApiModelProperty(value = "提示消息")
    private String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * 服务器时间
     */
    @ApiModelProperty(value = "服务器时间")
    private long timestamp = System.currentTimeMillis();

    /**
     * 操作是否成功
     *
     * @return
     */
    @ApiModelProperty(value = "操作是否成功")
    @JsonProperty(value = "success", index = 1)
    public boolean isOk() {
        return this.code == OK;
    }

    /**
     * 是否操作错误
     *
     * @return
     */
    @JsonIgnore
    public boolean isFailed() {
        return this.code != OK;
    }


    /**
     * 正常
     *
     * @return
     */
    public static ApiResult ok() {
        return new ApiResult().code(OK).msg("success");
    }
    /**
     * 正常
     * @param message
     * @return
     */
    public static ApiResult ok(String message) {
        return new ApiResult().code(OK).msg(message);
    }

    /**
     * 错误
     *
     * @return
     */
    public static ApiResult failed() {
        return new ApiResult().code(FAILED).msg("failed");
    }

    public static ApiResult failed(String message) {
        return new ApiResult().code(FAILED);
    }

    public ApiResult code(int code) {
        this.code = code;
        return this;
    }

    public ApiResult msg(String message) {
        this.message = message;
        return this;
    }

    public ApiResult data(T data) {
        this.data = data;
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
