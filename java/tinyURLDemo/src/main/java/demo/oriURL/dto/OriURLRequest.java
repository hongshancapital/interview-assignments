package demo.oriURL.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "原 URL 传入对象")
public class OriURLRequest {
    @ApiModelProperty(required = true, notes = "原 URL 路径文字")
    private String oriURL;
}
