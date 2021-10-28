package com.scdt.interview.assignments.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkParam {
    @NotEmpty(message = "短连接url不能为空")
    @ApiModelProperty("短连接url")
    private String shortUrl;
}
