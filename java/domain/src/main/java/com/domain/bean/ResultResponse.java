package com.domain.bean;

import com.domain.exception.ExceptionEnums;

/**
 * @author ：ji
 * @description：返回结果封装
 */
public class ResultResponse<T> {
    public static final int SUCCESS = 0;
    public static final String SUCC_MSG = "成功";

    private int code;
    private String message;
    private T Data;

    public ResultResponse(){
        this.code = SUCCESS;
        this.message = SUCC_MSG;
    }

    public ResultResponse(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public ResultResponse(T data) {
        this.code = SUCCESS;
        this.message = SUCC_MSG;
        this.Data = data;
    }

    /**
     * 通过异常枚举类ExceptionEnums初始化
     * @param exc
     */
    public ResultResponse(ExceptionEnums exc){
        this.code = exc.getCode();
        this.message = exc.getMessage();
    }

    /**
     * 网络请求异常，请稍后重试！
     * @return
     */
    public static ResultResponse fail(){
        return new ResultResponse(ExceptionEnums.FAIL);
    }

    /**
     * 成功状态-无数据
     * @return
     */
    public static ResultResponse success(){
        ResultResponse resp = new ResultResponse();
        resp.setCode(SUCCESS);
        resp.setMessage(SUCC_MSG);
        return resp;
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
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

}
