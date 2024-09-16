package com.yuanyang.hongshan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yuanyang
 * @date 2021/12/16 5:33 下午
 * @Describe
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO implements Serializable {

    private String longUrl;

    private String shortUrl;

    private String domain;

}
