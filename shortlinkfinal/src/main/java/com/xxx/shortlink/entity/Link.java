package com.xxx.shortlink.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Link {

    private Integer id;

    private String shortLink;

    private String originalLink;

    private Date createTime;

    private Date updateTime;

}
