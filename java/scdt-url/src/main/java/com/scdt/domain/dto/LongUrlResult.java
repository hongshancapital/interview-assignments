package com.scdt.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Data
@ApiModel("短链接转长链接返回体")
public class LongUrlResult extends BaseUrlResult {
    public LongUrlResult(String url){
        super(url);
    }
}
