package com.example.sequoiahomework.vo.url;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Irvin
 * @description 长链接转短链接的vo
 * @date 2021/10/9 19:55
 */
@Data
@ApiModel(value = "长链接短链接互相转换的请求数据体", description = "长链接短链接互相转换的请求数据体")
public class ChangeUrlVo {

    /**
     * 原始的长链接
     */
    @ApiModelProperty(value = "原始的链接", required = true)
    @NotBlank(message = "originalUrl参数不可为空")
    //@Pattern(regexp = "^(?=^.{3,255}$)(http(s)?:\\/\\/)?(www\\.)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\\d+)*(\\/\\w+\\.\\w+)*([\\?&]\\w+=\\w*)*$", message = "当前url不符合域名规范")
    private String originalUrl;

}
