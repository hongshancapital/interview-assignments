package com.skg.domain.demo.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author smith skg
 * @Date 2021/10/11 17:56
 * @Version 1.0
 */
@Data
public class ShortDomainParam {
    @NotNull(message = "短域名参数不能为空")
    @Size(max = 8,message = "短域名参数长度不超过8")
    String shortDomain;
}
