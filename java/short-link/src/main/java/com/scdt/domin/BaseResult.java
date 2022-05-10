package com.scdt.domin;

import com.scdt.constant.Consts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * BaseResult
 *
 * @author weixiao
 * @date 2022-04-26 11:34
 */
@Getter
@ApiModel(description = "基础结果")
public class BaseResult<T> {

    /** 成功 */
    @ApiModelProperty(value = "成功")
    private boolean success;

    /** 业务代码 0：成功 */
    @ApiModelProperty(value = "业务代码 0：成功")
    private int code;

    /** 提示信息 */
    @ApiModelProperty(value = "提示信息")
    private String msg;

    /** 数据 */
    @ApiModelProperty(value = "数据")
    private T data;

    private BaseResult(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<>(true, Consts.SUCCESS_CODE, Consts.SUCCESS_MSG, data);
    }

    public static <T> BaseResult<T> failure(ErrorCode error) {
        return new BaseResult<>(false, error.getCode(), error.getMsg(), null);
    }

    /**
     * 自定义错误描述信息
     */
    public static <T> BaseResult<T> failure(ErrorCode error, String msg) {
        return new BaseResult<>(false, error.getCode(), msg, null);
    }
}
