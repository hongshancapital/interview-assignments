package com.sunjinghao.shorturl.api.dto;

import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;


/**
 * 域名（链）bo实体
 *
 * @author sunjinghao
 */
@Data
@Valid
public class DomainMappingInfoDto  implements Serializable {


    private static final long serialVersionUID = 7618788855998450948L;

    /**
     * 唯一id
     */
    private Long id;

    /**
     * 长域名（长链）
     */
    private String url;

    /**
     * 短链 code
     */
    private String shortCode;

}
