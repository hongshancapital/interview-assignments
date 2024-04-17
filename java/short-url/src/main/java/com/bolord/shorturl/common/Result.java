package com.bolord.shorturl.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 统一数据响应类
 *
 * @param <T> data 属性的数据类型，无需 data 属性用 Void 泛型
 */
public class Result<T> {

    protected boolean success = false;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected Integer code;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected T data;

    public Result() {
        super();
    }

    public Result(boolean success) {
        super();
        this.success = success;
    }

    public Result(boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, Integer code, String message) {
        super();
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Result(boolean success, T data, String message) {
        super();
        this.success = success;
        this.data = data;
        this.message = message;
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
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("Result [success=%s, code=%s, message=%s, data=%s]", success, code, message, data);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + (success ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Result))
            return false;
        @SuppressWarnings("rawtypes")
        Result other = (Result) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (success != other.success)
            return false;
        return true;
    }

}
