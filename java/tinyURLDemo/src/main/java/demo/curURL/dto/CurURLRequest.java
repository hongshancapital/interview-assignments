package demo.curURL.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "短域名传入对象")
public class CurURLRequest {
    @ApiModelProperty(required = true, notes = "原 URL 路径文字")
    private String curURL;
}
