package com.example.domain.service.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: Domain
 * @Author DH
 * @Date: 2021/12/6 15:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Domain实体")
public class Domain {

    /**
     * 短域名
     */
    @ApiModelProperty("短域名")
    private String shortDomain;

    /**
     * 长域名
     */
    @ApiModelProperty("长域名")
    private String domain;

    /**
     * 主键id (对应的短域名)
     */
    private Long id;
}
