package url.converter.service;

import url.converter.facade.vo.LongUrlGetReq;
import url.converter.facade.vo.LongUrlGetRes;
import url.converter.facade.vo.ShortUrlAddReq;
import url.converter.facade.vo.ShortUrlAddRes;

/**
 * @author Wang Siqi
 * @date 2021/12/31
 */
public interface UrlConverterService {

    /**
     * 存储短域名
     */
    ShortUrlAddRes addShortUrl(ShortUrlAddReq req);

    /**
     * 获取长域名
     */
    LongUrlGetRes getLongUrl(LongUrlGetReq req);
}
