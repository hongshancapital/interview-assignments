package com.scdt.china.shorturl.service;

import com.scdt.china.shorturl.common.GlobalErrorCode;
import com.scdt.china.shorturl.common.ResultVo;
import com.scdt.china.shorturl.pojo.Url;
import com.scdt.china.shorturl.pojo.request.LongUrlRequest;
import com.scdt.china.shorturl.pojo.request.ShortUrlRequest;
import com.scdt.china.shorturl.pojo.response.LongUrlResponse;
import com.scdt.china.shorturl.pojo.response.ShortUrlResponse;
import com.scdt.china.shorturl.storage.DataStorage;
import com.scdt.china.shorturl.utils.JacksonUtil;
import com.scdt.china.shorturl.utils.ShortUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: zhouchao
 * @Date: 2021/12/02 23:32
 * @Description:
 */
@Slf4j
@Service
public class UrlService {

    @Autowired
    private DataStorage dataStorage;

    @Value("${shortUrl.host}")
    private String host;

    /**
     * @param shortUrlRequest {@link ShortUrlRequest} 获取短链接的请求实体
     * @return ResultVo
     */
    public ResultVo<ShortUrlResponse> getLongUrl(ShortUrlRequest shortUrlRequest) {
        String shortUrl = shortUrlRequest.getShortUrl();
        Url url = dataStorage.mapping(shortUrl);
        if (url == null) {
            log.debug("未查询到相关短链接：{}", shortUrl);
            return ResultVo.failure(GlobalErrorCode.NO_SHORT_URL, null);
        }
        log.debug("返回的长连接信息为：{}", JacksonUtil.toJson(url));
        ShortUrlResponse sr = new ShortUrlResponse();
        sr.setLongUrl(url.getLongUrl());
        return ResultVo.success(sr);
    }

    /**
     *
     * @param longUrlRequest {@link LongUrlRequest} 保存短链接对象
     * @return
     */
    public ResultVo<LongUrlResponse> saveShortUrl(LongUrlRequest longUrlRequest) {
        String longUrl = longUrlRequest.getLongUrl();

        String shortCode = ShortUrlUtils.shortUrl(longUrl);
        log.debug("生成的短链接码为：{}", shortCode);

        StringBuffer sb = new StringBuffer();
        String shortUrl = sb.append(host).append(shortCode).toString();

        Url url = new Url();
        url.setCreateTime(LocalDateTime.now());
        url.setLongUrl(longUrl);
        boolean result = dataStorage.saveUrl(shortUrl, url);
        if (result) {
            LongUrlResponse longUrlResponse = new LongUrlResponse();
            longUrlResponse.setShortUrl(shortUrl);
            return ResultVo.success(longUrlResponse);
        } else {
            return ResultVo.failure(GlobalErrorCode.SHORTURL_CREATE_FAIL, null);
        }
    }

}
