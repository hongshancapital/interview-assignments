package com.scdt.java.shortLink.web.param;

import com.scdt.java.shortLink.component.constant.ResponseStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@ApiModel(description = "响应结果")
public class Response {
    @ApiModelProperty(value = "响应码")
    private int status;
    @ApiModelProperty(value = "错误信息")
    private String errMsg;
    @ApiModelProperty(value = "响应内容")
    private String content;

    public static Response success(String content) {
        return new Response(ResponseStatus.SUCCESS.getStatus(), "", content);
    }

    public static Response fail(String errMsg) {
        return new Response(ResponseStatus.FAIL.getStatus(), errMsg, "");
    }

    public static Response systemError() {
        return new Response(ResponseStatus.SYSERROR.getStatus(), ResponseStatus.SYSERROR.getMsg(), "");
    }
}
