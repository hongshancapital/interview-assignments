package interview.assignments.service;

/**
 * 短链接服务
 * @author zhiran.wang
 * @date 2022/4/10 22:14
 */
public interface IShortUrlService {

    /**
     * 返回指定长域名对应的短域名
     * @param longUrl 长域名信息
     * @return 返回短域名信息
     */
    String getShortUrl(String longUrl);

    /**
     * 返回指定短域名对应的长域名
     * @param shortUrl 短域名信息
     * @return 返回长域名信息
     */
    String getLongUrl(String shortUrl);

}
