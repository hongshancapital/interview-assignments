package com.gc.shorturl.service.impl;

import com.gc.shorturl.entity.ShortUrl;
import com.gc.shorturl.mapper.UrlMapper;
import com.gc.shorturl.service.IShortUrlService;
import javafx.print.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

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

    static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3",
            "4", "5", "6", "7", "8", "9"
    };

    @Override
    public String convert2ShortUrl(String originalUrl) {
        String url = genShortUrl(originalUrl);
        ShortUrl data = new ShortUrl();
        data.setShortUrl(url);
        data.setOriginalUrl(originalUrl);
        List<ShortUrl> dataList = mapper.getByShortUrl(url);
        if (dataList.size() == 0) {
            //如果没有遇到相同的短地址则直接插入数据库
            mapper.insert(data);
        } else if (dataList.size() == 1) {
            //如果有一条已经短地址相同但是原网址不同的数据，则生成一条新的数据，并生成一个后缀，此时返回的短地址长度为7个字符
            if (!dataList.get(0).getOriginalUrl().equals(originalUrl)) {
                data.setIndex(1);
                data.setSuffix(chars[0]);
                mapper.insert(data);
            }
        } else {
            //查询是否存相同的数据
            ShortUrl tempData = mapper.getByAllUrl(url, originalUrl);
            if (tempData == null) {
                //未找到相同数据时，获取当前短地址的冲突次数，即序号+1
                int index = mapper.getMaxIndexByShortUrl(url) + 1;
                data.setIndex(index);
                int a = index / chars.length;
                int b = index % chars.length;
                String suffix = "";
                if (a > 0) {
                    if (a > chars.length)
                        throw new RuntimeException("超出数组边界");
                    suffix = chars[a - 1];
                }
                //序号转字符，最多两个字符，即允许62*62次冲突
                suffix += chars[b];
                data.setSuffix(suffix);
                mapper.insert(data);
            } else {
                data = tempData;
            }
        }
        //返回结果为短地址+后缀
        return data.getShortUrl() + data.getSuffix();
    }

    /**
     *  生成一个6个字符的短地址
     */
    String genShortUrl(String url) {
        String md5Code = DigestUtils.md5DigestAsHex(url.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            String subString = md5Code.substring(i * 6, Math.min(i * 6 + 6, md5Code.length()));
            long result = 0;
            for (int j = 0; j < subString.length(); j++) {
                result += (int) subString.charAt(j);
            }
            long index = 0x003D & result;
            sb.append(chars[(int) index]);
        }
        return sb.toString();
    }

    @Override
    public String convert2OriginalUrl(String shortUrl) {
        if (shortUrl.length() == 6) {//直接获取短网址对应的原地址
            String url = shortUrl.substring(0, 6);
            List<ShortUrl> dataList = mapper.getByShortUrl(url);
            if (dataList.size() > 0)
                return dataList.get(0).getOriginalUrl();
        }
        if (shortUrl.length() > 6) {//冲突后带后缀的地址
            String url = shortUrl.substring(0, 6);
            String suffix = shortUrl.substring(6);
            ShortUrl data = mapper.getByShortUrlAndSuffix(url, suffix);
            if (data != null)
                return data.getOriginalUrl();
        }
        return "";
    }
}
