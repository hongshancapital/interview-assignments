package com.example.baiyang.demo.model;

import com.example.baiyang.demo.validate.PatternUrl;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/15
 * @description: 请求传输类
 */
@Data
public class RequestDTO implements Serializable {

    /**
     * 长域名信息,需要校验是否是正确的格式
     */
    @PatternUrl
    @Size(max = 200)
    private String longUrl;

    /**
     * 短域名信息,需要校验是否是正确的格式
     */
    @Size(max = 8)
    private String shortUrl;

    /**
     * 加密算法
     */
    private String digest;

    /**
     * 全链路跟踪id
     */
    private String traceId;

    /**
     * 扩展字段
     */
    private String feature;
}
