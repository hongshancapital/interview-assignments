package pers.jenche.convertdomain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  Copyright (c) 2020 By www.jenche.cn
 * @author jenche E-mail:jenchecn@outlook.com
 * @date 2020/5/24 14:36
 * @description 消息输送
 */
@Data
@ApiModel(value = "响应数据")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResultDTO {
    @ApiModelProperty(value = "错误代码")
    private int code = 0;

    @ApiModelProperty(value = "错误消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Object data = null;
}
