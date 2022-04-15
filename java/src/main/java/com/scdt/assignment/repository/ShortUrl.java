package com.scdt.assignment.repository;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * @title ShortUrl.java
 * @description
 * @author
 * @date 2022-04-15 17:10:44
 */
@Data
@Accessors(chain = true)
@FieldNameConstants(innerTypeName = "FIELDS")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sys_shorturl")
@DynamicInsert
@DynamicUpdate
public class ShortUrl {

    public final static String DOMAIN = "domain.cn";

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.xiesx.fastboot.db.jpa.identifier.IdWorkerGenerator")
    private Long id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createTime;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;

    /**
     * 长链接地址
     */
    @Column(nullable = false)
    private String longUrl;

    /**
     * 长链接Hash
     */
    @Column(nullable = false)
    private Integer longHash;

    /**
     * 短连接地址
     */
    @Column(nullable = false)
    private String shortUrl;

    /**
     * 短链接Hash
     */
    @Column(nullable = false)
    private Integer shortHash;

    /**
     * 持久化前
     */
    @PrePersist
    public void onPrePersist() {
        // 计算长链接的Hash值
        this.longHash = HashUtil.bkdrHash(this.longUrl);
        // 计算短链接
        this.shortUrl = StrUtil.format("https://{}/{}", DOMAIN, RandomUtil.randomString(8));
        // 计算长链接的Hash值
        this.shortHash = HashUtil.bkdrHash(this.shortUrl);
    }
}
