package com.redwood.shorturl.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@ApiModel(value = "通用ResponseBody 返回类")
public class Result {
    @ApiModelProperty(value = "code 码", notes = "com.redwood.shorturl.constant.ResponseEnum")
    @Setter
    @Getter
    private int status = 200;
    @ApiModelProperty(value = "响应描述")
    @Setter
    @Getter
    private String message = "success";
    @ApiModelProperty(value = "响应接口返回数据")
    @Setter
    @Getter
    private Object data;

    public Result() {
    }

    private Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private Result(Object data) {
        this.data = data;
    }

    public static Result error(int status, String message) {
        return new Result(status, message);
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Result result = (Result) o;
//        if (data == null && ((Result) o).data == null) {
//            return status == result.status && message.equals(result.message);
//        } else if (data != null && ((Result) o).data != null) {
//            return status == result.status && message.equals(result.message) && data.equals(result.data);
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(status, message, data);
//    }
}
