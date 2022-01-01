package url.converter.facade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wang Siqi
 * @date 2021/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("获取长链接响应结果")
public class LongUrlGetRes {

    @ApiModelProperty("长链接")
    private String longUrl;
}
