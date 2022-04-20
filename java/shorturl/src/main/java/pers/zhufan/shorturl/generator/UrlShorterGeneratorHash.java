package pers.zhufan.shorturl.generator;

import org.springframework.stereotype.Component;
import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;
import pers.zhufan.shorturl.domain.shorturl.ShorterWithSimpleString;
import pers.zhufan.shorturl.storage.ShorterStorage;
import pers.zhufan.shorturl.util.UrlGenerateHashUtil;

import javax.annotation.Resource;

/**
 * @Author zhufan
 * @Date 2022-04-18 20:50
 * @ClassName UrlShorterGeneratorHash
 * @Description 采用Hash的方式生成短链接
 */
@Component
public class UrlShorterGeneratorHash implements UrlShorterGenerator {

    @Resource
    private ShorterStorage shorterStorage;

    @Override
    public ShorterUrl generate(String key, int lenth, String url) {
        String shorter = UrlGenerateHashUtil.generate(key, lenth, url);
        while (shorterStorage.get(shorter) != null) {
            shorter = UrlGenerateHashUtil.generate(key, lenth, url);
        }
        ShorterWithSimpleString newShorter = new ShorterWithSimpleString(shorter);
        shorterStorage.save(url, newShorter);
        return newShorter;
    }

}
