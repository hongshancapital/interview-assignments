package com.hs.shortlink.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.hs.shortlink.domain.constant.ResultStatusEnum;
import com.hs.shortlink.exception.BusinessException;
import com.hs.shortlink.service.PersistenceService;
import com.hs.shortlink.service.UrlTransformService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Dangerous
 * @time: 2022/5/13 17:07
 */
@Slf4j
@Service
public class UrlTransformServiceImpl implements UrlTransformService {

    //Caffeine是guava的升级版本, 因使用了 Window TinyLfu 回收策略，提供了一个近乎最佳的命中率
    private static final Cache<String, String> CACHE = Caffeine.newBuilder()
            .maximumSize(1000000)//100W个缓存
            .expireAfterWrite(7, TimeUnit.DAYS)//7天过期
            .removalListener((RemovalListener<String, String>) (key, value, cause) ->
                    System.out.println("key:" + key + ", value:" + value + ", 删除原因:" + cause.toString()))
            .build();

    //2^30 - 1 = 0x3FFFFFFF
    private static final int INF_30 = 0x3FFFFFFF;
    //61 = 0x0000003D
    private static final int INF_D = 0x0000003D;
    //获取短域名的基数
    private static final char[] METAS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    @Resource
    private PersistenceService persistenceService;

    @Override
    public String getShortUrl(String longUrl, Integer length) {
        try {
            return dogGetShortUrl(longUrl, length);
        } catch (Exception e) {
            log.error("生成短域名失败", e);
        }
        return null;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        try {
            final String longUrl = CACHE.getIfPresent(shortUrl);
            if (StringUtils.isBlank(longUrl)) {
                throw new BusinessException(ResultStatusEnum.LINK_NOT_EXISTS);
            }
            return longUrl;
        } catch (Exception e) {
            log.error("根据短域名获取长域名出现异常", e);
        }
        return null;
    }

    /**
     * 短域名处理方法
     *
     * @param longUrl 长域名
     * @param length  短域名长度
     * @return 长域名
     */
    private String dogGetShortUrl(String longUrl, int length) throws IOException {
        //存储超过上限，则下线编码服务并抛出异常


        String cacheShortUrl = CACHE.getIfPresent(longUrl);
        if (StringUtils.isBlank(cacheShortUrl)) {
            Random random = new Random();
            //每次随机生成两个秘钥key,分别拼接到头部和尾部
            StringBuilder randomKey1 = new StringBuilder();
            StringBuilder randomKey2 = new StringBuilder();
            for (int i = 0; i < length / 2; i++) {
                randomKey1.append(METAS[random.nextInt(METAS.length)]);
                randomKey2.append(METAS[random.nextInt(METAS.length)]);
            }
            //原始域名进行MD5得到32位的字符串
            String md5Code = DigestUtils.md5DigestAsHex(randomKey1.append(longUrl.trim()).append(randomKey2).toString().getBytes());
            //会生成4个长度为shortUrlLength的短连接，随机选择一个
            List<String> resUrlList = new ArrayList<>();
            //分成4短，每段8位字符串
            for (int i = 0; i < 4; i++) {
                String subString = StringUtils.substring(md5Code, i * 8, (i + 1) * 8);
                // 这里需要使用 long 型来转换，因为 Integer.parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用long ，则会越界
                long hexLong = INF_30 & Long.parseLong(subString, 16);
                StringBuilder sbBuilder = new StringBuilder();
                for (int j = 0; j < length; j++) {
                    // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                    long index = INF_D & hexLong;
                    // 把取得的字符拼接
                    sbBuilder.append(METAS[(int) index]);
                    // 每次循环按位右移 shortUrlLength / 2 位
                    hexLong = hexLong >> (length / 2);
                }
                resUrlList.add(sbBuilder.toString());
            }
            cacheShortUrl = resUrlList.get(random.nextInt(4));
            CACHE.put(longUrl, cacheShortUrl);
            CACHE.put(cacheShortUrl, longUrl);
            persistenceService.persist(longUrl, cacheShortUrl);
            persistenceService.persist(cacheShortUrl, longUrl);
        }
        return cacheShortUrl;
    }

    @Override
    public Cache<String, String> getCache() {
        return CACHE;
    }

}
