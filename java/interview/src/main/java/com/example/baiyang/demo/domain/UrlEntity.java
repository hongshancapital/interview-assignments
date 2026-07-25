package com.example.baiyang.demo.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/15
 * @description: 域名实体类
 */
@Data
public class UrlEntity {

    /**
     * 用户id，可用于权限校验
     */
    private Long userId;

    /**
     * 短域名信息
     */
    @NotNull(message = "参数不能为空")
    private String shortUrl;

    /**
     * 长域名信息
     */
    @NotNull(message = "参数不能为空")
    private String longUrl;

    /**
     * 生成短域名的时间
     */
    @Pattern(regexp = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$"
            ,message ="时间格式不符合规范")
    private Date gmtCreated;

    /**
     * 扩展字段
     */
    private String feature;
}
