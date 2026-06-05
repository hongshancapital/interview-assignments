package com.skg.domain.demo.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author smith skg
 * @Date 2021/10/11 17:56
 * @Version 1.0
 */
@Data
public class LongDomainParam {
    @NotNull(message = "长域名参数不能为空")
    String longDomain;
}
