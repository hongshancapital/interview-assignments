package com.xwt.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 域名服务bean
 *
 * @author xiwentao
 * @date: 2021-07-21
 */
@Data
public class DomainNameRequest implements Serializable {
    /**
     * UID
     */

    private static final long serialVersionUID = 1234567890L;

    /**
     * 长域名
     */
    @NotBlank(message = "长域名URL不能为空")
    private String longUrl;
}
