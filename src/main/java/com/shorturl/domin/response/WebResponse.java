package com.shorturl.domin.response;

import com.shorturl.common.CodeMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户接口返回结果
 *
 * @param <T> 数据实体类型
 * @author noodle
 */
@ApiModel("用户接口返回结果")
public class WebResponse<T> implements Serializable {

    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private int code;

    /**
     * 状态短语
     */
    @ApiModelProperty("状态短语")
    private String msg;

    /**
     * 数据实体
     */
    @ApiModelProperty("数据实体")
    private T data;

    /**
     * 定义为private是为了在防止在controller中直接new
     *
     * @param data
     */
    private WebResponse(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private WebResponse(CodeMsg codeMsg) {
        if (codeMsg == null)
            return;
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    /**
     * 业务处理成功返回结果，直接返回业务数据
     *
     * @param data
     * @return
     */
    public static <T> WebResponse<T> success(T data) {
        return new WebResponse<T>(data);
    }

    /**
     * 业务处理信息
     *
     * @param serverError
     * @param <T>
     * @return
     */
    public static <T> WebResponse<T> info(CodeMsg serverError) {
        return new WebResponse<T>(serverError);
    }

    /**
     * 业务处理成功
     *
     * @param serverError
     * @param <T>
     * @return
     */
    public static <T> WebResponse<T> success(CodeMsg serverError) {
        return new WebResponse<T>(serverError);
    }

    /**
     * 业务处理失败
     *
     * @param serverError
     * @param <T>
     * @return
     */
    public static <T> WebResponse<T> error(CodeMsg serverError) {
        return new WebResponse<T>(serverError);
    }

    /**
     * 只有get没有set，是为了防止在controller使用set对结果修改，从而达到一个更好的封装效果
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}

