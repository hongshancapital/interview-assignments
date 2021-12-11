package com.scdt.china.shorturl.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: zhouchao
 * @Date: 2021/11/26 16:36
 * @Description:
 */
@Data
@ApiModel(description = "连接对象")
public class Url {

    @ApiModelProperty(value = "长连接",name = "longUrl")
    private String longUrl;

    @ApiModelProperty(value = "创建时间",name = "createTime")
    private LocalDateTime createTime;

}
