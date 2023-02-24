package translation.model;

import lombok.SneakyThrows;
import translation.util.DateUtil;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: hello
 * @since: 2023/2/21
 */
//@Data
public class UrlMapBean implements Serializable {

    private static final long serialVersionUID = -8416412797750547531L;
    /**
     * 长链接
     */
    String longUrl;
    /**
     * 短链接
     */
    String shortUrl;
    /**
     * 创建时间 -- 用于清理缓存 LRU
     */
    long creatIime;
    /**
     * 点击率 -- 用于清理缓存 LFU
     */
    AtomicInteger clickRate = new AtomicInteger();

    public UrlMapBean() {

    }

    public UrlMapBean(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.creatIime = DateUtil.getCurrentDateTime();
    }

    @SneakyThrows
    public String toString() {
        String str = null;
        str = MessageFormat.format("长链接:{0};短链接:{1};" +
                        "创建时间:{2};点击率:{3};"
                , longUrl, shortUrl, DateUtil.formatDateToSSS(creatIime), clickRate);
        return str;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public long getCreatIime() {
        return creatIime;
    }

    public void setCreatIime(long creatIime) {
        this.creatIime = creatIime;
    }

    public AtomicInteger getClickRate() {
        return clickRate;
    }

    public void setClickRate(AtomicInteger clickRate) {
        this.clickRate = clickRate;
    }
}