package url.converter.facade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * @author Wang Siqi
 * @date 2021/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("短链接存储请求参数")
public class ShortUrlAddReq {

    @NotEmpty
    @Size(min = 2)
    @ApiModelProperty("短链接")
    private String longUrl;
}
