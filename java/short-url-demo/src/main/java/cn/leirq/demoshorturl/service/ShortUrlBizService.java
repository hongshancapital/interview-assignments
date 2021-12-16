package cn.leirq.demoshorturl.service;

/**
 * 短url生成业务接口
 * @author Ricky
 */
public interface ShortUrlBizService {

    /**
     * 根据长url创建并保存长短url
     * @param longUrl
     * @return
     */
    String createAndSaveByLongUrl(String longUrl);

    /**
     * 根据短url读取数据返回长url
     * @param shortUrl
     * @return
     */
    String getLongUrlByShortUrl(String shortUrl);
}
