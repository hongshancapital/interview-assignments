package javaassignment.lcg.entity;

import java.io.Serializable;

/**
 * @Author: 栾晨光
 * @Date: 2021-03-18 18:55
 * @Email 10136547@qq.com
 * @Description: http结果
 */
public class Result<T> implements Serializable {
    /**
     * 返回状态
     */
    private int code;

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

    /**
     * 返回提示信息
     */
    private String message;



    private Object data;

    public Result() {

    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
