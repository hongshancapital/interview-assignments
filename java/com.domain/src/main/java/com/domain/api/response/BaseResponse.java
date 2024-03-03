package com.domain.api.response;

import com.domain.utils.web.enums.HttpResponseCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * http 请求响应基础类
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse <T> implements java.io.Serializable {

    private static BaseResponse instance = new BaseResponse();

    @ApiModelProperty(value = "响应编码",required = true,example = "",notes = "",dataType ="String")
    private Integer code;

    @ApiModelProperty(value = "响应描述",required = true,example = "",notes = "",dataType ="String")
    private String desc;

    @ApiModelProperty(value = "数据页",required = false,example = "",notes = "",dataType ="Object")
    private  T  data;

    /**
     * 基础响应
     * @param  code 响应编码
     * @param  desc 响应描述
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> create(Integer code, String desc) {
        instance.setCode(code);
        instance.setDesc(desc);
        return instance;
    }
    /**
     * 基础响应-代返回数据
     * @param  code 响应编码
     * @param  desc 响应描述
     * @param  data 数据页
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> create(Integer code, String desc,T data) {
        instance.setCode(code);
        instance.setDesc(desc);
        instance.setData(data);
        return instance;
    }
    /**
     * 基础响应 成功
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> buildSuccess(){
        return create(HttpResponseCodeEnum.SUCCESS.getCode(),HttpResponseCodeEnum.SUCCESS.getMessage());
    }
    /**
     * 基础响应 成功
     * @param  data 数据页
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> buildSuccess(T data){
        return create(HttpResponseCodeEnum.SUCCESS.getCode(),HttpResponseCodeEnum.SUCCESS.getMessage(),data);
    }
    /**
     * 基础响应 成功
     * @param  data 数据页
     * @param  msg 自定义消息
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> buildSuccess(T data,String msg){
        return create(HttpResponseCodeEnum.SUCCESS.getCode(),msg,data);
    }
    /**
     * 基础响应 失败
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> buildFail(){
        return create(HttpResponseCodeEnum.FAIL.getCode(),HttpResponseCodeEnum.FAIL.getName());
    }
    /**
     * 基础响应 失败
     * @param  msg 自定义消息
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> buildFail(String msg){
        return create(HttpResponseCodeEnum.FAIL.getCode(),msg);
    }
    /**
     * 基础响应 失败
     * @param  httpResponseCodeEnum 错误响应枚举
     * @param  msg 自定义消息
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> buildFail(HttpResponseCodeEnum httpResponseCodeEnum,String msg){
        return create(httpResponseCodeEnum.getCode(),msg);
    }
    /**
     * 基础响应 失败
     * @param  httpResponseCodeEnum 错误响应枚举
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> buildFail(HttpResponseCodeEnum httpResponseCodeEnum){
        return create(httpResponseCodeEnum.getCode(),httpResponseCodeEnum.getMessage());
    }
}
