package com.hszb.shorturl.manager.generate;

import com.hszb.shorturl.manager.storage.ShortUrlStorage;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/18 5:15 下午
 * @Version: 1.0
 * @Description:
 */

public class Md5ShortUrlGenerator implements ShortCodeGenerator{

    private static final int len = 6;

    @Override
    public  String generateCode(String longUrl) {
        if (null == longUrl) {
            return null;
        }
        String md5Hex = DigestUtils.md5DigestAsHex(longUrl.getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            String subString = md5Hex.substring(i * 8, (i + 1) * 8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong(subString, 16);
            StringBuilder outChars = new StringBuilder();
            for (int j = 0; j < len; j++) {
                long index = 0x0000003D & lHexLong;
                outChars.append(ShortUrlGeneratorFactory.charArray[(int) index]);
                lHexLong = lHexLong >> len;
            }
            String shortCode = outChars.toString();
            // 查找短码是否存在
            if (existsShortUrl(shortCode)) {
                continue;
            } else {
                return shortCode;
            }
        }
        // 如果生成的四个短码都已存在了 返回null
        return null;
    }

    private boolean existsShortUrl(String shortCode) {
        return null != ShortUrlStorage.getInstance().queryLongUrl(shortCode);
    }
}
