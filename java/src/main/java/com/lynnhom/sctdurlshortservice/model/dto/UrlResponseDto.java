package com.lynnhom.sctdurlshortservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: 短链接生成结果响应实体类
 * @author: Lynnhom
 * @create: 2021-10-28 10:35
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("短链接生成结果")
public class UrlResponseDto {
    @ApiModelProperty("原链接")
    private String originalUrl;
    @ApiModelProperty("短链接结果")
    private String shortUrl;
    @ApiModelProperty("短链接失效时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireDate;

}
