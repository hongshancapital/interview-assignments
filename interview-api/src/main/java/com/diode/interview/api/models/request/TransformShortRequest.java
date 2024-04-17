package com.diode.interview.api.models.request;

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
@ApiModel("短链接转换成长连接请求体")
public class TransformShortRequest {
    @ApiModelProperty("待转换链接")
    private String url;

    public static void validate(TransformShortRequest request){
        Preconditions.checkArgument(Objects.nonNull(request), "请求参数不能为空！");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(request.getUrl()), "url必填！");
        Preconditions.checkArgument(MyURL.isShortURL(request.getUrl()), "url必须为短链接！");
    }
}
