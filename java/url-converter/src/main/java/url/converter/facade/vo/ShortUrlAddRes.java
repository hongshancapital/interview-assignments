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
@ApiModel("短链接存储响应结果")
public class ShortUrlAddRes {

    @ApiModelProperty("短链接")
    private String shortUrl;
}
