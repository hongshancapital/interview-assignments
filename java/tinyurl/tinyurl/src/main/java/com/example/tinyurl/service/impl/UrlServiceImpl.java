package com.example.tinyurl.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.example.tinyurl.service.UrlService;
import com.example.tinyurl.util.LRU;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangtian
 * @version 1.0
 * @Project: UrlServiceImpl
 * @Title: tinyurl
 * @Package com.example.tinyurl.service.impl
 * @Description:长短链接互转服务接口实现类
 * @date 2021/12/19 14:39
 */
@Service
public class UrlServiceImpl implements UrlService {

    LRU links = LRU.getInstance();

    String defaultUrl = "www.baidu.com";

    @Override
    public String toTinyurl(String url) {

        //转换长链接为短链接，并放入缓存头部
        String tinyurl = gererateTinyUrl(url);
        links.put(tinyurl, url);
        return tinyurl;

    }

    @Override
    public String fromTinyurl(String url) {
        //访问短域名，更新缓存
        String ret = links.get(url);
        return ret != null ? ret.toString() : defaultUrl;
    }

    /**
     * 算法
     * 将长链接转换为短链接
     * @param url 长链接
     * @return 短链接
     */
    public String gererateTinyUrl(String url) {
        String key = "scdt" ;
        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
                "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
                "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
                "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
                "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
                "U" , "V" , "W" , "X" , "Y" , "Z"

        };
        // 对传入网址进行 MD5 加密
        String md5Result = SecureUtil.md5(key+url);

        String md5Result_3 = md5Result.substring(2 * 8, 2 * 8 + 8);

        long hexLong = 0x3FFFFFFF & Long.parseLong (md5Result_3, 16);
        StringBuffer ret = new StringBuffer() ;
        for ( int j = 0; j < 6; j++) {
            long index = 0x0000003D & hexLong;
            ret.append(chars[( int ) index]);
            hexLong = hexLong >> 5;
        }
        return ret.toString();
    }
}