package com.scdt.shortlink.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xbhong
 * @date 2022/4/13 22:12
 */
@Getter
@Setter
public class ResultBean implements Serializable {

    private static final int SUCCESS_CODE = 200;
    private static final int FAIL_CODE = 500;

    private Integer code;
    private String msg;
    private Object data;

    public ResultBean() {
    }

    public ResultBean(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultBean success(String msg, Object data) {
        return new ResultBean(SUCCESS_CODE, msg, data);
    }

    public static ResultBean fail(String msg, Object data) {
        return new ResultBean(FAIL_CODE, msg, data);
    }

    public static ResultBean create(Integer code, String msg, Object data) {
        return new ResultBean(code, msg, data);
    }

}