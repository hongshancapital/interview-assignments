package com.sunjinghao.shorturl.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;


/**
 * 域名（链）vo实体
 *
 * @author sunjinghao
 */
@Data
@ApiModel(value = "DomainMappingInfoVo对象", description = "长短域名映射关系信息")
public class DomainMappingInfoVo implements Serializable {


    private static final long serialVersionUID = 7875661902873615741L;

    @NotEmpty(message = "长域名不能为空")
    @URL(message = "长域名不符合URL格式")
    @ApiModelProperty("长域名")
    private String url;


}
