package homework.shorturl.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "链接转换对象")
public class UrlTransDTO {

    @ApiModelProperty(value = "长链接")
    private String url;

    @ApiModelProperty(value = "短链接")
    private String shortUrl;
}
