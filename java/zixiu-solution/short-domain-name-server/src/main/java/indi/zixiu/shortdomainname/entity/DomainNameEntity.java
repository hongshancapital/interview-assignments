package indi.zixiu.shortdomainname.entity;

public class DomainNameEntity {
    private long shortName;
    private String longName;
    private int createdTime;  // 创建该域名的时间，Unix 时间戳，单位为秒

    public DomainNameEntity(long shortName, String longName, int createdTime) {
        this.shortName = shortName;
        this.longName = longName;
        this.createdTime = createdTime;
    }

    public long getShortName() {
        return shortName;
    }

    public void setShortName(long shortName) {
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
