package com.wuhui.shorturl.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "short_url")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public ShortUrl setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public ShortUrl setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ShortUrl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
