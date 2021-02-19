/*
 * Copyright (C) 2021 hongsan, Inc. All Rights Reserved.
 */
package com.example.shorturl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.example.shorturl.dao.ShortUrlDao;
import com.example.shorturl.domain.ShortUrl;

/**
 * 短链接服务，缓存加数据库
 * com.example.shorturl.service shorturl
 *
 * @create mencius 2021-02-18 19:41
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private static final Logger logger = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    @Autowired
    private ShortUrlDao shortUrlDao;
    @Autowired
    private RedisService redisService;

    /**
     * https://blog.csdn.net/dihuangtian01/article/details/98849862
     * 传入32位md5值
     *
     * @param md5
     * @return
     */
    public static String[] shortUrl(String md5) {
        // 要使用生成 URL 的字符
        String[] chars = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"
        };

        String[] resUrl = new String[4];

        for (int i = 0; i < 4; i++) {

            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算，超过30位的忽略
            String sTempSubString = md5.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }

    @Override
    public String queryLongUrl(String code) {
        String fullUrl = redisService.get(code);
        if (!StringUtils.hasLength(fullUrl)) {
            final ShortUrl byShortUrl = shortUrlDao.findByShortUrl(code);
            if (byShortUrl != null) {
                fullUrl = byShortUrl.getLongUrl();
                logger.info("query from db :{} {}", code, fullUrl);
            }
        }
        return fullUrl;
    }

    @Override
    public String genCode(String fullUrl, int expirtTime) {
        String code = redisService.get(fullUrl);
        if (!StringUtils.hasLength(code)) {
            final String md5 = DigestUtils.md5DigestAsHex(fullUrl.getBytes());
            final String[] codes = shortUrl(md5);
            code = codes[0];
            redisService.set(code, fullUrl, expirtTime);
            redisService.set(fullUrl, code, expirtTime);
            logger.info("set redis  :{} {}", code, fullUrl);
            final ShortUrl byLongUrl = shortUrlDao.findByLongUrl(fullUrl);
            if (byLongUrl == null) {
                shortUrlDao.insert(new ShortUrl(fullUrl, code));
                logger.info("set db  :{} {}", code, fullUrl);
            }
        }
        return code;
    }

}
