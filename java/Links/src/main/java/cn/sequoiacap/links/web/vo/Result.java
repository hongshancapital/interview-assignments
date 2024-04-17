package cn.sequoiacap.links.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author : Liushide
 * @date :2022/4/6 11:36
 * @description : 统一API响应结果格式封装
 */
@ApiModel
@Getter
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 6308315887056712045L;

    /**
     * 响应码
     */
    @ApiModelProperty(name="code", value="响应码", example ="200")
    private Integer code;

    /**
     * 提示消息
     */
    @ApiModelProperty(name="message", value="提示消息", example ="成功")
    private String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(name="data", value="响应数据", example ="{\"longLink\":\"https://www.abc.com/app/tb-source-app/aiguangjiepc/content/index.html?spm=a21bo&user_name=lucas\", \"shortCode\":\"ra6vAv\"}")
    private T data;


    /**
     * 设置结果值，有返回数据
     * @param resultCode
     * @param data
     */
    private Result(ResultCodeEnum resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }


    /**
     * 只返回状态 成功
     * @return
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 成功返回数据
     * @param data
     * @return
     */
    public static <T> Result<T> success(T data) {
        return success(ResultCodeEnum.SUCCESS, data);
    }

    /**
     * 成功返回数据
     * @param resultCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(ResultCodeEnum resultCode, T data) {
        return new Result<T>(resultCode, data);
    }

    /**
     * 只返回状态 失败
     * @param resultCode
     * @return
     */
    public static <T> Result<T> failure(ResultCodeEnum resultCode) {
        return  failure(resultCode, null);
    }

    /**
     * 失败返回数据
     * @param resultCode
     * @param data
     * @return
     */
    public static <T> Result<T> failure(ResultCodeEnum resultCode, T data) {
        return new Result<T>(resultCode, data);
    }

}
