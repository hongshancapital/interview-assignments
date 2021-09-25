package org.example.sgenerate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 链接映射详情
 * @author liuyadu
 */
@ApiModel("链接映射详情")
public class UrlMappingInfo {
    @ApiModelProperty("短链接ID")
    private String id;

    @ApiModelProperty("短链接")
    private String shortUrl;

    @ApiModelProperty("长连接")
    private String originalUrl;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("过期时间")
    private Date expiryTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }


    /**
     * 是否已过期
     *
     * @return true 已过期 | false 未过期
     */
    public boolean isExpired() {
        if (expiryTime != null) {
            if (expiryTime.getTime() < System.currentTimeMillis()) {
                return true;
            }
        }
        return false;
    }
}
