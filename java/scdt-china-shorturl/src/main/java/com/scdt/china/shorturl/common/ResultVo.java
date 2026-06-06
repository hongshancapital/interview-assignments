package com.scdt.china.shorturl.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

/**
 * @Author zhouchao
 * @Date 2021/11/26 14:51
 * @Description 返回结果包装类
 **/
@Data
@Slf4j
public class ResultVo<T> {

    /**
     * code=0，代表成功
     */
    @ApiModelProperty(value = "结果返回码",name = "code")
    private String code;

    /**
     * 描述信息
     */
    @ApiModelProperty(value = "结果信息",name = "msg")
    private String msg;

    /**
     * 数据
     */
    @ApiModelProperty(value = "结果数据",name = "data")
    private T data;

    // private String traceId = MDC.get("traceId");

    public ResultVo(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVo() {
    }

    /**
     * 成功
     *
     * @return
     */
    public static <T> ResultVo<T> success() {
        return handleRs(GlobalErrorCode.SUCCESS.getCode(), GlobalErrorCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static <T> ResultVo<T> success(T data) {
        return handleRs(GlobalErrorCode.SUCCESS.getCode(), GlobalErrorCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功
     *
     * @param msg
     * @param data
     * @return
     */
    public static <T> ResultVo<T> success(String msg, T data) {
        return handleRs(GlobalErrorCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 失败
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static <T> ResultVo<T> failure(String code, String msg, T data) {
        return handleRs(code, msg, data);
    }

    /**
     * 失败
     *
     * @param globalErrorCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultVo<T> failure(GlobalErrorCode globalErrorCode, T data) {
        return handleRs(globalErrorCode.getCode(), globalErrorCode.getMessage(), data);
    }


    private static <T> ResultVo<T> handleRs(String code, String msg, T data) {
        return new ResultVo(code, msg, data);
    }


    /**
     * 调用远程接口异常时固定格式
     *
     * @param tClass
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> T remoteErrorRs(Class<T> tClass, String code, String msg, Object data) {
        try {
            Constructor<T> constructor = tClass.getConstructor(code.getClass(), msg.getClass(), Object.class);
            return constructor.newInstance(code, msg, data);
        } catch (Exception e) {
            log.error("调用远程接口异常", e);
        }
        return null;
    }

}
