package com.example.baiyang.demo.model;

import com.example.baiyang.demo.validate.PatternUrl;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/15
 * @description: 响应传输类
 */
@Data
public class ResponseDTO implements Serializable {

    /**
     * 长域名信息,需要校验是否是正确的格式
     */
    @PatternUrl
    private String longUrl;

    /**
     * 短域名信息,需要校验是否是正确的格式
     */
    @Size(max = 8)
    private String shortUrl;
}
