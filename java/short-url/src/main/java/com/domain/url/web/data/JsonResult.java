package com.domain.url.web.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回结果统一封装类
 */
@Data
@ApiModel("短链接服务返回结果统一封装类")
public class JsonResult<T> {
    private static final String OK = "ok";

    @ApiModelProperty(value = "状态", notes = "当且仅当返回值=ok时表示请求成功，其他均为请求失败")
    private String status;

    @ApiModelProperty("成功 or 失败信息")
    private String msg;

    @ApiModelProperty("返回数据")
    private T data;

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> result = new JsonResult<>();
        result.setStatus(OK);
        result.setMsg(OK);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> ok() {
        return ok(null);
    }

    public static JsonResult<?> fail(String status, String msg) {
        status = status == null ? "fail" : status;
        if (OK.equals(status)) {
            throw new RuntimeException("ok is not fail");
        }
        JsonResult<?> result = new JsonResult<>();
        result.setStatus(status);
        result.setMsg(msg);
        return result;
    }

    @JsonGetter
    @ApiModelProperty("时间戳")
    public long getTimestamp() {
        return System.currentTimeMillis();
    }
}
