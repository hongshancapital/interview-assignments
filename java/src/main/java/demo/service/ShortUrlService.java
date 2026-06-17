package demo.service;

/**
 * @ClassName: ShortUrlService
 * @Description: 短长链接转换服务类
 * @author Xia
 * @version V1.0
 * @Date 2021/12/15
 */
public interface ShortUrlService {
    /**
     * 获取长连接
     * @param shortUrl 长域名信息
     * @return
     */
    String getLongUrl(String shortUrl);

    /**
     * 获取短链接
     * @param longUrl 短域名信息
     * @return
     */
    String getShortUrl(String longUrl);
}
