package cn.sequoiacap.domain.entity;

/**
 *  对应的实体对象
 * @author liuhao
 * @date 2021/12/10
 */
public class DomainEntity {

    /**
     * 唯一标识
     */
    private String uuid;
    /**
     * 短域名
     */
    private String shortDomain;
    /**
     * 长域名
     */
    private String longDomain;
    /**
     * 设置一个过期时间 这个看具体业务来定义，也可以一直保留，但是在网上看到一种方案，就是如果将短连接设置为临时的，会有利于数据的收集
     * 对用户进行画像或是大数据分析，如果是固定的，就没有那么方便，因为每次都是同一个短连接。这个看具体业务。这里我就没有实现了
     *
     */
    private long expiredTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getShortDomain() {
        return shortDomain;
    }

    public void setShortDomain(String shortDomain) {
        this.shortDomain = shortDomain;
    }

    public String getLongDomain() {
        return longDomain;
    }

    public void setLongDomain(String longDomain) {
        this.longDomain = longDomain;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

}
