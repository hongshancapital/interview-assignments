package com.xiaoxi666.tinyurl.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/10
 * @Version: 1.0
 * @Description: 短域名存储接口qin请求对象
 */
@ApiModel(description = "短域名存储接口qin请求对象")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreParam {
    /**
     * 长url
     */
    @ApiModelProperty(value = " 常规长url", name = "longUrl", required = true, example = "https://xie.infoq.cn/article/c66df7f79e46553362981cdf6")
    private String longUrl;
    /**
     * 存储等级，主要用于支持临时短链和长期短链
     * 0：长期。永不失效
     * 1：临时。失效时间为1小时。目前简单实现，定义为1小时失效。后续可扩展为自定义，比如用时间轮驱动到期删除。
     */
    @ApiModelProperty(value = "存储等级，主要用于支持临时短链和长期短链。0为长期，永不失效；1为临时，失效时间为1小时。", name = "level", example = "1")
    private int level = 0;

}
