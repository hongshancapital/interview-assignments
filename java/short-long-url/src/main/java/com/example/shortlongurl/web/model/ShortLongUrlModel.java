package com.example.shortlongurl.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@ApiModel("短链接模型")
@Data
@ToString
@Builder
public class ShortLongUrlModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "短链接", example = "http://t.cn/ABC")
    private String shortUrl;
    @ApiModelProperty(value = "长链接", example = "http://abc.com.cn/ABC/332/weweeww")
    private String longUrl;
    @ApiModelProperty(value="长链接的hash" ,example = "ABCDE223UUK")
    private String key;
    @ApiModelProperty(value="过期时间" ,example = "1633178919152")
    private long expire;

    public boolean isExpire(){
        return System.currentTimeMillis() > this.expire;
    }
}
