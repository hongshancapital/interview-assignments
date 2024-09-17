package com.alexyuan.shortlink.common.variant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CacheVariant {

    /**
     * 各发号器生成的最终唯一码, 作为PK使用
     */
    private String uniq_code;

    /**
     * 本次生成的短域名
     */
    private String short_url;

    /**
     * 本次短链接对应的长域名
     */
    private String long_url;

    /**
     * 生成时间
     */
    private String generate_time;

    public CacheVariant(String uniq_code, String short_url, String long_url, String generate_time) {
        this.uniq_code = uniq_code;
        this.short_url = short_url;
        this.long_url = long_url;
        this.generate_time = generate_time;
    }
}
