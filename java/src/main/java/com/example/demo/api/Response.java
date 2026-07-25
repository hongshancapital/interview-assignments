/**
 * @(#)Response.java, 12月 26, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo.api;


import com.example.demo.constant.DemoErrorEnum;
import io.swagger.annotations.*;
import lombok.*;
import org.apache.commons.lang3.tuple.Pair;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "返回类")
public class Response<T> {

    @ApiModelProperty(value = "code 码", notes = "com.example.demo.constant.DemoErrorEnum")
    private String code;

    @ApiModelProperty(value = "响应数据")
    private T result;

    @ApiModelProperty(value = "响应描述")
    private String message;

    private Response(String message, T t) {
        this.result = t;
        Pair<Integer, String> pair = DemoErrorEnum.parseException(message);
        this.setCode(String.valueOf(pair.getLeft()));
        this.setMessage(pair.getRight());
    }

    public static <T> Response<T> createSuccess(T result) {
        return new Response(DemoErrorEnum.SUCCESS.toString(), result);
    }

    public static <T> Response<T> createFail(String message) {
        return new Response(message, null);
    }

}
