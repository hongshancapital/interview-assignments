package com.gc.shorturl.service.impl;

import com.gc.shorturl.mapper.UrlMapper;
import com.gc.shorturl.service.IShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Author: GC
 * @Date: 2021/3/4
 */
@Service
public class ShortUrlServiceImpl implements IShortUrlService {
    @Autowired
    UrlMapper mapper;

    @Override
    public String convert2ShortUrl(String longUrl) {
        String shortUrl = "";
        shortUrl = genShortUrl(longUrl);
        mapper.insert(shortUrl, longUrl);
        System.out.println(shortUrl);
        return shortUrl;
    }

    String genShortUrl(String url) {
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3",
                "4", "5", "6", "7", "8", "9"
        };
        String md5Code = DigestUtils.md5DigestAsHex(url.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String subString = md5Code.substring(i * 4, i * 4 + 4);
            long result = 0;
            for (int j = 0; j < 4; j++) {
                result += (int) subString.charAt(j);
            }
            long index = 0x003D & result;
            sb.append(chars[(int) index]);
        }
        return sb.toString();
    }

    @Override
    public String convert2OriginalUrl(String shortUrl) {
        return mapper.getByShortUrl(shortUrl);
    }
}
