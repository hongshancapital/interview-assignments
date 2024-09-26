package url.converter.service;

/**
 * @author Wang Siqi
 * @date 2021/12/31
 */
public interface CheckService {

    void checkLongUrl(String longUrl);

    void checkShortUrl(String shortUrl);
}
