package com.yilong.shorturl.model;

import com.yilong.shorturl.common.exception.BusinessException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {

    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_ERROR = 500;
    public static final int STATUS_BAD_REQUEST = 400;

    private Boolean success = true;
    private Integer status = STATUS_SUCCESS;
    private T data;
    private Error error;

    public static class Builder {

        public static <T> Result<T> success() {
            return success(null);
        }

        public static <T> Result<T> success(T data) {
            Result<T> result = new Result<>();
            result.data = data;
            return result;
        }

        public static <T> Result<T> error(Integer code, String message) {
            return error(STATUS_ERROR, code, message);
        }

        public static <T> Result<T> error(Integer status, Integer code, String message) {
            Result<T> result = new Result<>();
            result.status = status;
            result.error = new Error();
            result.error.setMessage(message);
            result.error.setCode(code);
            result.success = false;
            return result;
        }

        public static <T> Result<T> error(BusinessException businessException) {
            return error(businessException.getStatus(), businessException.getCode(), businessException.getMessage());
        }
    }
}
