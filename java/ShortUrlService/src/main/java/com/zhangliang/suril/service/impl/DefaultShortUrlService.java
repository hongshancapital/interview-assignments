package com.zhangliang.suril.service.impl;

import com.zhangliang.suril.data.DataStoreManage;
import com.zhangliang.suril.service.ShortUrlService;
import com.zhangliang.suril.util.AssertUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 默认的短网址服务
 *
 * @author zhang
 * @date 2021/12/02
 */
@Service
public class DefaultShortUrlService implements ShortUrlService {

    private List<Character> characters = new ArrayList();

    @Value("${config.short-url-length}")
    private Integer shortUrlLength;

    @Value("${config.short-url-prefix}")
    private String prefix;

    @Resource
    private DataStoreManage dataStoreManage;


    /**
     * 保存url
     *
     * @param oriUrl url
     * @return {@link String}
     */
    @Override
    public String saveUrl(String oriUrl) {
        AssertUtils.isUrl(oriUrl);

        String shortUrl = processShortUrl(oriUrl);
        dataStoreManage.store(shortUrl,oriUrl);
        return shortUrl;
    }

    /**
     * 根据原始的url ，得到短url 检查是否已经存在短url，如果存在则递归调用，知道不存在相同的url
     *
     * @param url url
     * @return {@link String}
     */
    private String processShortUrl(String url) {

        String shortUrl = prefix;
        for (int i = 0; i < shortUrlLength; i++) {
            int random = (int) (Math.random() * 62);
            shortUrl += characters.get(random);
        }
        if (dataStoreManage.exists(shortUrl)) {
            shortUrl = processShortUrl(url);
        }
        return shortUrl;
    }


    /**
     * 根据短url，获取原始url
     *
     * @param shortUrl url
     * @return {@link String}
     */
    @Override
    public String getUrl(String shortUrl) {
        AssertUtils.isUrl(shortUrl);
        return dataStoreManage.fetch(shortUrl);
    }

    @PostConstruct
    private void init() {
        // a：65 A:97
        // 0:48  1:49 9:57
        // 总共有 26+26+10 个 字符用来做随机拼凑
        for (int i = 0; i < 26; i++) {
            characters.add(Character.valueOf((char) (i + 65)));
            characters.add(Character.valueOf((char) (i + 97)));
        }

        for (int i = 0; i < 10; i++) {
            characters.add(String.valueOf(i).charAt(0));
        }
    }
}
