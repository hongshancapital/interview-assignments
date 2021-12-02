package com.zhangliang.suril.controller.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用来封装返回结果
 *
 * @author zhang
 * @date 2021/12/02
 */
@Getter
@Setter
@ApiModel(value = "返回对象", description = "通用的返回对象")
public class BaseResult<T> {

    @ApiModelProperty(value = "返回数据", name = "playLoad", notes = "真正的数据在这里")
    private T playLoad;

    @ApiModelProperty(value = "返回消息", name = "msg", notes = "如果有错误，这里不会为空")
    private String msg;

    @ApiModelProperty(value = "返回码", name = "code", notes = "返回的代码")
    private Integer code;

}
