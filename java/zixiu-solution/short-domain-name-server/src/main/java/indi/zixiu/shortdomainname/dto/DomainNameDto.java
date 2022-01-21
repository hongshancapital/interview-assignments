package indi.zixiu.shortdomainname.dto;

public class DomainNameDto {
    private String shortName;
    private String longName;
    private int createdTime;  // 创建该域名的时间，Unix 时间戳，单位为秒

    public DomainNameDto(String shortName, String longName, int createdTime) {
        this.shortName = shortName;
        this.longName = longName;
        this.createdTime = createdTime;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }
}
