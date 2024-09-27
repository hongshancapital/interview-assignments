package org.goofly.shortdomain.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : goofly
 * @Email: 709233178@qq.com
 */
@Data
@ApiModel
@Accessors(chain = true)
public class ResultBase<T> {
    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("错误信息")
    private String errMsg;

    @ApiModelProperty("数据")
    private T data;

    @ApiModelProperty("是否成功")
    private boolean success;

    public static <T> ResultBase successRsp(T data) {
        return new ResultBase<T>()
                .setSuccess(Boolean.TRUE)
                .setData(data);
    }

    public static <T> ResultBase errorRsp(String errMsg, int code) {
        return new ResultBase<T>()
                .setErrMsg(errMsg)
                .setCode(code);
    }

    public static <T> ResultBase errorRsp(String errMsg) {
        return errorRsp(errMsg,500);
    }
}
