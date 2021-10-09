package com.scdt.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Data
@ApiModel("长链接转短链接返回体")
public class ShortUrlResult extends BaseUrlResult{

    public ShortUrlResult(String url){
        super(url);
    }
}
