package ks.sequoia.aware;

import ks.sequoia.eobj.DomainEObj;

/**
 * @author jing.tong
 */
public interface CacheServiceAware {
    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * @param longDomain 长域名
     */
     public DomainEObj queryEObjByLongDomain(String longDomain);

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     * @param shortDomain 短域名
     */
    public DomainEObj queryEObjByShortDomain(String shortDomain);


}
