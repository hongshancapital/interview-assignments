package com.scdt.job.lsx.model;

import com.scdt.job.lsx.enums.ErrorCodeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lsx
 */
public class ResponseResult<T> implements Serializable{
        private Integer code;
        private String msg;
        private boolean success;
        private T data;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseResult<T> buildFail(T data, ErrorCodeEnum codeEnum) {
            ResponseResult<T> response = new ResponseResult<>();
            response.setCode(codeEnum.getCode());
            response.setMsg(codeEnum.getMsg());
            response.setSuccess(false);
            response.setData(data);
            return response;
        }

        public static <T> ResponseResult<T> buildSuccess(T data) {
            ResponseResult<T> response = new ResponseResult<>();
            response.setCode(ErrorCodeEnum.Success.getCode());
            response.setSuccess(true);
            response.setData(data);

            return response;
        }

}
