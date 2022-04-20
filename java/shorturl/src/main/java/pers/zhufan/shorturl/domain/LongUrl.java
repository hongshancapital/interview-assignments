package pers.zhufan.shorturl.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "pers.zhufan.shorturl.domain.LongUrl", description = "入参对象")
public class LongUrl implements Serializable {

    /**
     * 长链接
     * 【必填】
     */
    @ApiModelProperty(value = "longUrl", required = true, dataType = "String", name = "长链接")
    private String longUrl;

    public LongUrl(String longUrl) {
        this.setLongUrl(longUrl);
    }

}
