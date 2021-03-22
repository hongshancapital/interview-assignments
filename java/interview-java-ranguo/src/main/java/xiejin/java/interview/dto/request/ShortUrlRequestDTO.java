package xiejin.java.interview.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import xiejin.java.interview.util.FormatVerifyUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xiejin
 * @date 2021/3/20 12:53
 */
@Data
@Accessors(chain = true)
public class ShortUrlRequestDTO {

    @NotBlank(message = "短网址不能为空")
    @ApiModelProperty(value = "短网址",required = true)
    @Pattern(regexp = FormatVerifyUtil.REGEX_SHORT_URL,message = "短网址格式不匹配，请检查")
    public String shortUrl;

}
