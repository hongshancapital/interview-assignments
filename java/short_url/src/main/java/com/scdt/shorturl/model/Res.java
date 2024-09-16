package com.scdt.shorturl.model;


public class Res<T> {

    public enum ErrorCode {
        /**
         *  正常，错误，验证失败，过期，没权限
         */
        OK(0), ERROR(1), INVALID(2), EXPIRED(3), FORBIDDEN(4);
        private int code;

        ErrorCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private boolean success;
    private Integer code;
    private String msg;
    private T data;

    public Res() {
    }

    public Res(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        if (ErrorCode.OK.getCode() == code) this.success = true;
    }

    public Res(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        if (ErrorCode.OK.getCode() == code) this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
        if (ErrorCode.OK.getCode() == code) this.success = true;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> Res<T> success(T data) {
        return new Res<>(ErrorCode.OK.getCode(), "ok", data);
    }

    public static <T> Res<T> error(String msg, T data) {
        return new Res<>(ErrorCode.ERROR.getCode(), msg, data);
    }

}
