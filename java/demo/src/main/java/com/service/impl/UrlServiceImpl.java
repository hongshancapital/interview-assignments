package com.service.impl;

import com.common.ServiceException;
import com.service.UrlService;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UrlServiceImpl implements UrlService {


    private static final Map<String,String> cache=new ConcurrentHashMap<>();

    @Override
    public String addUrl(String url) {
        if (!urlCheck(url)){
            throw new ServiceException("请输入合法url");
        }
        String[] shortUrls = urlHandler(url);
        for (int i = 0; i < shortUrls.length; i++) {
            if (!cache.containsKey(shortUrls[i])){
                cache.put(shortUrls[i],url);
                return shortUrls[i];
            }else {
                if (cache.get(shortUrls[i]).equals(url)){
                    return shortUrls[i];
                }
            }
        }
        return "";
    }

    public static boolean urlCheck(String url){
        if (Objects.isNull(url)||url.isEmpty()){
            return false;

        }        url = url.toLowerCase();
        String regex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}"
                + "|"
                + "([0-9a-z_!~*'()-]+\\.)*"
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\."
                + "[a-z]{2,6})"
                + "(:[0-9]{1,5})?"
                + "((/?)|"
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return  url.matches(regex);
    }

    @Override
    public String getUrl(String shortUrl) {
        String url=cache.get(shortUrl);
        if (Objects.isNull(url)){
            throw new ServiceException("url不存在");
        }
        return url;
    }

    public  String[] urlHandler(String url) {
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };

        String sMD5EncryptResult = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(url.getBytes());
            byte[] digest = md.digest();
            sMD5EncryptResult = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            String sTempSubString = sMD5EncryptResult.substring(i * 8, (i+1) * 8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                long index = 0x0000003D & lHexLong;
                outChars += chars[(int) index];
                lHexLong = lHexLong >> 5;
            }
            resUrl[i] = outChars;
        }
        return resUrl;
    }
}
