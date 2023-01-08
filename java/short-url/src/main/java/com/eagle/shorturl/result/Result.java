package com.eagle.shorturl.result;

import lombok.Data;

/**
 * @author eagle
 * @description
 */
@Data
public class Result<V> {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 描述信息
     */
    private String message;
    /**
     * 数据
     */
    private V data;

    public Result<V> success(V data) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getMessage();
        this.data = data;
        return this;
    }

    public Result<V> fail(ResultCodeEnum resultCode, String message) {
        this.code = resultCode.getCode();
        this.message = message;
        return this;
    }



}
