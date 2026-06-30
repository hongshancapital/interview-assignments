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
@ApiModel("长链接传输层模型")
@Accessors(chain = true)
public class LongUrlDto {
    /**
     * 长链接
     * http://halo.baicloud.top/archives/idea%E5%AF%BC%E5%85%A5%E9%9D%9Emaven%E9%A1%B9%E7%9B%AE#toc-head-0
     * https://halo.baicloud.top/archives/idea%E5%AF%BC%E5%85%A5%E9%9D%9Emaven%E9%A1%B9%E7%9B%AE#toc-head-0
     */
    @URL(message = "URL格式非法")
    @NotNull(message = "URL为空")
    @Length(max = 4000, message = "URL长度非法")
    @ApiModelProperty(value = "长链接", required = true,
                      example = "http://halo.baicloud.top")
    private String url;
}
