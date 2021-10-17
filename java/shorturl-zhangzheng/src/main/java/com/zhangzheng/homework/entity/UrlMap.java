package com.zhangzheng.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/9 下午1:55
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shortUrl;
    private String longUrl;
    private Date createTime;
    private Date modifyTime;
}

