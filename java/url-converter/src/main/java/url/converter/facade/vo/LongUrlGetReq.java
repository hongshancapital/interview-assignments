package url.converter.facade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Wang Siqi
 * @date 2021/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("获取长链接请求参数")
public class LongUrlGetReq {

    @NotEmpty
    @ApiModelProperty("短链接")
    private String shortUrl;
}
