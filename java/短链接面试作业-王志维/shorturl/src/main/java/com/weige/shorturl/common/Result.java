package com.weige.shorturl.common;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @ClassName Result
 * @Description 结果封装
 * @Author zwwang14
 * @Date 2022/1/20 16:20
 * @Version 1.0
 */
public class Result implements Serializable {
    private static Integer SUCCESS = 200;
    private static Integer ERROR = 500;

    private static String SUCCESS_MSG = "请求成功";
    private static String ERROR_MSG = "请求失败";

    private String msg;
    private Integer code;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success(String msg, Object data){
        Result result = new Result();
        result.setCode(SUCCESS);
        if (!StringUtils.isEmpty(msg)){
            result.setMsg(SUCCESS_MSG);
        }
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


    public static Result failed(String msg){
        Result result = new Result();
        result.setCode(ERROR);
        if (!StringUtils.isEmpty(msg)){
            result.setMsg(ERROR_MSG);
        }
        result.setMsg(msg);
        return result;
    }
}
