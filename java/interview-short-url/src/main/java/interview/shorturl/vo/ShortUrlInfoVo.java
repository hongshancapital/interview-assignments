package interview.shorturl.vo;

import interview.shorturl.dao.po.ShortUrlInfo;

import java.util.Date;

/**
 * 接口转换数据
 *
 * @author: ZOUFANQI
 **/
public class ShortUrlInfoVo {
    private final String realUrl;
    private final String shortUrl;
    private final Date expireOn;


    public ShortUrlInfoVo(String realUrl, String shortUrl, Date expireOn) {
        this.realUrl = realUrl;
        this.shortUrl = shortUrl;
        this.expireOn = expireOn;
    }

    public static ShortUrlInfoVo buildVo(ShortUrlInfo info) {
        if (info == null) {
            return null;
        }
        return new ShortUrlInfoVo(info.getRealUrl(), info.getShortUrl(), info.getExpireOn());
    }

    public String getRealUrl() {
        return realUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public Date getExpireOn() {
        return expireOn;
    }
}
