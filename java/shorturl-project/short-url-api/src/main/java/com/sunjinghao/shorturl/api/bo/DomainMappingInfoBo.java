package com.sunjinghao.shorturl.api.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * 域名（链）bo实体
 *
 * @author sunjinghao
 */
@Data

public class DomainMappingInfoBo  implements Serializable {


    private static final long serialVersionUID = 6212334228244986775L;


    /**
     * 长域名（长链）
     */
    @ApiModelProperty("长域名")
    private String url;

    /**
     * 短链 code
     */
    private String shortCode;


}
