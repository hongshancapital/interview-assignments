package org.example.shorturl.modal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * @author bai
 * @date 2022/3/19 19:45
 */
@Getter
@Setter
@ApiModel("短链接传输层模型")
@Accessors(chain = true)
public class ShortUrlDto {
    /**
     * 短链接
     * http://t.cn/12345678
     * https://t.cn/12345678
     */
    @URL(message = "URL格式非法")
    @NotNull(message = "URL为空")
    @Length(max = 23, message = "URL长度非法")
    @ApiModelProperty(value = "短链接", required = true, example = "https://bai.cn/12345678")
    private String url;
}
