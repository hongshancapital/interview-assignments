package com.jk.shorturl.controller.bean;

import java.util.HashMap;

/**
 * 简易返回对象封装器
 */
public class ResponseEntity extends HashMap<String, Object> {

    /**
     * 静态方法，成功的时候返回
     *
     * @param message 成功提示消息
     * @return 新的返回对象
     */
    public static ResponseEntity success(String message) {
        ResponseEntity response = new ResponseEntity();
        response.setSuccess(Boolean.TRUE);
        response.setMessage(message);
        return response;
    }

    /**
     * 静态方法，失败的时候返回
     *
     * @param message 失败消息
     * @return 新的返回对象
     */
    public static ResponseEntity failure(String message) {
        ResponseEntity response = new ResponseEntity();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(message);
        return response;
    }

    /**
     * 非静态方法，单独设置处理结果标志
     *
     * @param success 处理成功传入 true/  处理失败传入 false
     * @return 当前对象
     */
    public ResponseEntity setSuccess(Boolean success) {
        if (success != null) put("success", success);
        return this;
    }

    /**
     * 非静态方法，单独设置提示消息
     *
     * @param message
     * @return 当前对象
     */
    public ResponseEntity setMessage(String message) {
        if (message != null) put("message", message);
        return this;
    }

    /**
     * 非静态方法，向返回值中放入自定义信息
     *
     * @param key   key
     * @param value value
     * @return 当前对象
     */
    public ResponseEntity setAny(String key, Object value) {
        if (key != null && value != null) put(key, value);
        return this;
    }
}
