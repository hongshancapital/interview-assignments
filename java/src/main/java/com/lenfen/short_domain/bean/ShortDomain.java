package com.lenfen.short_domain.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 短域名实体信息
 */
@Getter
@Setter
@ApiModel(value = "短域名信息")
public class ShortDomain {

    /**
     * 长域名
     */
    @ApiModelProperty(value = "长域名", required = true, example = "https://github.com/luckyzerg")
    private String fullUrl;

    /**
     * 短域名
     */
    @ApiModelProperty(value = "短域名", required = true, example = "TfLsrvp6")
    private String shortUrl;

    /**
     * 编码时间
     */
    @ApiModelProperty(value = "编码时间", required = true, example = "2022-04-22 12:28:49")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime encodeTime;

    /**
     * 编码次数
     */
    @ApiModelProperty(value = "编码次数", dataType = "java.lang.Long", required = true, example = "1")
    private AtomicLong encodeCount;

    /**
     * 解码次数
     */
    @ApiModelProperty(value = "解码次数", dataType = "java.lang.Long", required = true)
    private AtomicLong decodeCount;
}
