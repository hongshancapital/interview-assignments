package xiejin.java.interview.dto.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import xiejin.java.interview.util.FormatVerifyUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xiejin
 * @date 2021/3/20 11:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LongUrlRequestDTO {

    @NotBlank(message = "原网址不能为空")
    @Pattern(regexp = FormatVerifyUtil.REGEX_URL, message = "请输入有效的网址http(s)")
    @ApiModelProperty(value = "原网址",required = true)
    private String originalUrl;


}
