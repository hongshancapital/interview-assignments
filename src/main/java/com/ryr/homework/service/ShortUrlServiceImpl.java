package com.ryr.homework.service;

import com.ryr.homework.common.util.ShortUrlConvertUtil;
import com.ryr.homework.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Value("${short.url.domain}")
    private String domainUrl;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private ShortUrlConvertUtil shortUrlConvertUtil;

    @Override
    public String getLongUrlByShortUrl(String shortUrl) {
        String longUrl = shortUrlRepository.getLongUrl(shortUrl);
        if (StringUtils.isEmpty(longUrl)) {
            return "无效的短域名";
        }
        return longUrl;
    }

    @Override
    public String getShortUrlByLongUrl(String longUrl) {
        //生成短链
        String shortUrl = shortUrlConvertUtil.getShortUrl(longUrl);

        //判断是否已保存长域名
        if (!shortUrlRepository.existShortUrl(shortUrl)) {
            //没有则保存
            shortUrlRepository.saveLongUrl(longUrl, shortUrl);
            //返回短域名
            return domainUrl + shortUrl;
        }

        //获取已存在的长域名 如果存在且与要存入的长域名相同 则直接返回
        String existLongUrl = shortUrlRepository.getLongUrl(shortUrl);
        if (existLongUrl.equals(longUrl)) {
            //已存在的长链 直接return
            return domainUrl + shortUrl;
        }

        //处理冲突的短域名
        return this.saveConflictedUrl(longUrl);

    }

    /**
     * 如果短链冲突 则给url拼接上后缀，重新生成
     * 最多冲突3次
     * @param longUrl
     * @return
     */
    public String saveConflictedUrl(String longUrl) {
        String mulStr = "_mul";
        String mulLongUrl = longUrl;
        for (int i = 0; i < 3; i++) {
            mulLongUrl += mulStr;
            //生成短链
            String shortUrl = shortUrlConvertUtil.getShortUrl(mulLongUrl);
            //判断是否已保存长域名
            if (!shortUrlRepository.existShortUrl(shortUrl)) {
                //没有则保存
                shortUrlRepository.saveLongUrl(longUrl, shortUrl);
                //返回短域名
                return domainUrl + shortUrl;
            }

            String existLongUrl = shortUrlRepository.getLongUrl(shortUrl);
            if (existLongUrl.equals(longUrl)) {
                //已存在的长链 直接return
                return domainUrl + shortUrl;
            }
            //如果还是冲突 则继续进行下一次循环
        }
        return "生成短链失败";
    }
}
