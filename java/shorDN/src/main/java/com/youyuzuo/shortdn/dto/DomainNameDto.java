package com.youyuzuo.shortdn.dto;

import com.youyuzuo.shortdn.bo.QueryResult;
import com.youyuzuo.shortdn.bo.SaveResult;
import com.youyuzuo.shortdn.entry.DomainRecordEntry;
import com.youyuzuo.shortdn.util.IdUtil;
import com.youyuzuo.shortdn.util.SecurityUtil;

import java.time.LocalDateTime;

public class DomainNameDto {
    private String shortDN;
    private String longDN;
    private String longDnMd5;
    private String longDnSha1;

    public DomainNameDto(String shortDN, String longDN) {
        assert longDN != null;
        this.shortDN = shortDN;
        this.longDN = longDN;
        try {
            this.longDnMd5 = SecurityUtil.md5(longDN);
            this.longDnSha1 = SecurityUtil.sha1(longDN);
        } catch (Exception e) {
            throw new Error("System error");
        }
    }

    public DomainRecordEntry toDomainRecordEntry(){
        DomainRecordEntry entry = new DomainRecordEntry();
        entry.setId(IdUtil.nexId());
        entry.setShortDN(this.shortDN);
        entry.setLongDN(this.longDN);
        entry.setLongDnMd5(this.longDnMd5);
        entry.setLongDnSha1(this.longDnSha1);
        entry.setCreateTime(LocalDateTime.now());
        return entry;
    }

    public SaveResult toSaveResult(){
        return new SaveResult(this.shortDN, this.longDN);
    }

    public QueryResult toQueryResult(){
        return new QueryResult(this.shortDN, this.longDN);
    }

    public static DomainNameDto fromDomainRecordEntry(DomainRecordEntry entry){
        DomainNameDto dto = new DomainNameDto(entry.getShortDN(), entry.getLongDN());
        return dto;
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

    public String getLongDnSha1() {
        return longDnSha1;
    }
}
