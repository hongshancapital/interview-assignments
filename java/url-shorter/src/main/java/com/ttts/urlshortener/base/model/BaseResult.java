package com.ttts.urlshortener.base.model;

import com.ttts.urlshortener.base.exception.BusinessException;
import lombok.Data;

/**
 * 返回数据模型
 */
@Data
public class BaseResult<T> {
    String code;
    String message;
    T data;

    protected BaseResult() {};

    public boolean isSuccess() {
        return BaseResultCodeEnums.SUCCESS.getCode().equals(this.code);
    }

    public static <T> BaseResult success(T data) {
        BaseResult<T> result = new BaseResult();
        result.setCode(BaseResultCodeEnums.SUCCESS.getCode());
        result.setMessage(BaseResultCodeEnums.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> BaseResult success(String msg, T data) {
        BaseResult<T> result = new BaseResult();
        result.setCode(BaseResultCodeEnums.SUCCESS.getCode());
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

    public static <T> BaseResult of(BaseResultCodeEnums codeEnums, T data) {
        if (codeEnums == null) {
            throw new IllegalArgumentException("codeEnums不能为空");
        }
        BaseResult<T> result = new BaseResult();
        result.setCode(codeEnums.getCode());
        result.setMessage(codeEnums.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> BaseResult of(BusinessException e) {
        if (e == null) {
            throw new IllegalArgumentException("e不能为空");
        }
        BaseResult<T> result = new BaseResult();
        result.setCode(e.getCode());
        result.setMessage(e.getMsg());
        return result;
    }
}
