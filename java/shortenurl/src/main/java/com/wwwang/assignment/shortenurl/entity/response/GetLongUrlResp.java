package com.wwwang.assignment.shortenurl.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;

@ApiModel(description = "长域名实体")
@Getter
public class GetLongUrlResp  implements Serializable {

    @ApiModelProperty(value="长域名", example="https://www.baidu.com/")
    private String url;

    public GetLongUrlResp(String url){
        this.url = url;
    }

}
