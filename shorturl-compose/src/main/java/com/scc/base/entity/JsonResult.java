package com.scc.base.entity;

import com.scc.base.constant.StatusCode;

/**
 * @author renyunyi
 * @date 2022/4/24 3:03 PM
 * @description the result wrapper class
 **/
public class JsonResult {

    private int code;

    private String message;

    private Object data;

    public JsonResult(int code, String message){
        this.code = code;
        this.message = message;
    }

    /**
     * simplify to use for not response any data when response is success
     */
    public static JsonResult getSuccessResult(){
        return new JsonResult(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMsg());
    }

    /**
     * simplify to use when response is success!
     */
    public static JsonResult getSuccessResult(Object data){
        JsonResult result = getSuccessResult();
        result.setData(data);
        return result;
    }

    public static JsonResult getErrorResult(){
        return new JsonResult(StatusCode.FAIL.getCode(), StatusCode.FAIL.getMsg());
    }

    public static JsonResult getErrorResult(String msg){
        return new JsonResult(StatusCode.FAIL.getCode(), msg);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
