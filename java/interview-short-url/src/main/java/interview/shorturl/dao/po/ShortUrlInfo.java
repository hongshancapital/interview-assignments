package interview.shorturl.dao.po;

import lombok.Data;

import java.util.Date;

/**
 * 模拟数据库存储数据
 *
 * @author: ZOUFANQI
 **/
@Data
public class ShortUrlInfo {
    /**
     * 模拟大对象
     */
    private final byte[] testBigObj = new byte[1024 * 1024 * 5];
    /**
     * 唯一ID
     */
    private Long id;
    /**
     * 真实地址
     */
    private String realUrl;
    /**
     * 短地址
     */
    private String shortUrl;
    /**
     * 创建日期
     */
    private Date createOn;
    /**
     * 失效日期
     */
    private Date expireOn;

}
