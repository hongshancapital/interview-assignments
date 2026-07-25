package ks.sequoia.eobj;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 域名的存储 信息
 * @since 2022-01-08
 * @author jing.tong
 */
@Data
public class DomainEObj extends AbstractEObj{
    /**
     * 主键
     */
    private Long domainId;
    /**
     * 长域名
     */
    private String longDomain;
    /**
     * 短域名
     */
    private String shortDomain;
    /**
     * 访问次数,用来缓存预热
     */
    private int accessTimes;


}
