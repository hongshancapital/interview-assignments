package com.diode.interview.api.models.request;

import com.diode.interview.domain.constant.enums.TransformerEnum;
import com.diode.interview.domain.entity.MyURL;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Data
@ApiModel("长链接转换成短链接请求体")
public class TransformLongRequest {
    @ApiModelProperty("待转换链接")
    private String url;
    @ApiModelProperty("选择的算法，算法列表：\"MD5\"...")
    private String alg = "MD5";
    @ApiModelProperty("过期秒数")
    private int expireSecs = 3600;

    private static final int TWO_HOURS = 7200;

    public static void validate(TransformLongRequest request) {
        Preconditions.checkArgument(Objects.nonNull(request), "请求参数不能为空！");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(request.getUrl()), "url必填！");
        Preconditions.checkArgument(MyURL.isLongURL(request.getUrl()), "url必须为长链接！");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(request.getAlg()), "算法不能为空！");
        Preconditions.checkArgument(Objects.nonNull(TransformerEnum.getByType(request.getAlg())), "指定算法不合法或暂不支持！");
        Preconditions.checkArgument(request.getExpireSecs() > 0 && request.getExpireSecs() <= TWO_HOURS, "生效时间必须合法且不大于两小时！");
    }
}
