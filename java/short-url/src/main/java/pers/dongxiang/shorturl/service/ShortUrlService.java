package pers.dongxiang.shorturl.service;

/**
 * @ProjectName short-url
 * @Package pers.dongxiang.shorturl.service
 * @ClassName ShortUrlService
 * @Description 短地址服务接口
 * @Company lab
 * @Author dongxiang
 * @Date 10/29/2021 5:56 PM
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
public interface ShortUrlService {

    String createShortUrl(String originUrl);

    String getOriginUrl(String shortUrl);

}
