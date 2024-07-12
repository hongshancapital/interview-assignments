package com.example.shorturl.dao;


import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "short_url_mapping")
public class URLMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = ValidGroup.Crud.Create.class)
//    @Size(max = 10, message = "URL 长度不能超过10个字符", groups = ValidGroup.Crud.Create.class)
    @Size(max = 6144, message = "URL 长度不能超过6144个字符", groups = ValidGroup.Crud.Create.class)
    @URL(groups = ValidGroup.Crud.Create.class,message = "长链接必须是有效的URL")
    private String lurl;

    @Column(name = "lurl_hash")
    private Long lurlHash;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    public URLMapping() {

    }

    public URLMapping(String lurl, Long lurlHash) {
        this.lurl = lurl;
        this.lurlHash = lurlHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLurl() {
        return lurl;
    }

    public void setLurl(String lurl) {
        this.lurl = lurl;
    }

    public Long getLurlHash() {
        return lurlHash;
    }

    public void setLurlHash(Long lurlHash) {
        this.lurlHash = lurlHash;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "URLMapping{" +
                "id=" + id +
                ", lurl='" + lurl + '\'' +
                ", lurlHash=" + lurlHash +
                ", createTime=" + createTime +
                '}';
    }
}
