package com.youyuzuo.shortdn.entry;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class DomainRecordEntry implements Serializable {

    private Long id;
    private String shortDN;
    private String longDN;
    private String longDnMd5;
    private String longDnSha1;
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortDN() {
        return shortDN;
    }

    public void setShortDN(String shortDN) {
        this.shortDN = shortDN;
    }

    public String getLongDN() {
        return longDN;
    }

    public void setLongDN(String longDN) {
        this.longDN = longDN;
    }

    public String getLongDnMd5() {
        return longDnMd5;
    }

    public void setLongDnMd5(String longDnMd5) {
        this.longDnMd5 = longDnMd5;
    }

    public String getLongDnSha1() {
        return longDnSha1;
    }

    public void setLongDnSha1(String longDnSha1) {
        this.longDnSha1 = longDnSha1;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainRecordEntry entry = (DomainRecordEntry) o;
        return Objects.equals(longDN, entry.longDN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortDN, longDN);
    }
}
