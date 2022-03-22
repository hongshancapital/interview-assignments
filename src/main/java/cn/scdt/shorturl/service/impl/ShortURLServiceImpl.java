package cn.scdt.shorturl.service.impl;


import cn.scdt.shorturl.exception.SystemErrorType;
import cn.scdt.shorturl.service.ShortURLService;
import cn.scdt.shorturl.utils.HashUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ShortURLServiceImpl implements ShortURLService {

    //自定义长链接防重复字符串
    private static final String DUPLICATE = "*";
    //创建长短链接MAP,采用线程安全的HASHMAP
    public static final ConcurrentHashMap<String,String> Long2Short = new ConcurrentHashMap<>();
    //创建短长链接MAP,采用线程安全的HASHMAP
    public static final ConcurrentHashMap<String,String> Short2Long = new ConcurrentHashMap<>();

    @Override
    public String getFullURL(String shortURL) {
        if(Short2Long.containsKey(shortURL)){
            return Short2Long.get(shortURL);
        }
        return SystemErrorType.NOT_EXISTS_FULLURL.getMesg();

    }

    @Override
    public String getShortURL(String longURL,String shortUrl) {
        if(Long2Short.containsKey(longURL)){
            return Long2Short.get(longURL);
        }else{
            //判断短链接MAP中是否已存在
            if(Short2Long.containsKey(shortUrl)){
                //若存在，且长地址不一样，则重新hash
                if(!longURL.equals(Short2Long.get(shortUrl))){
                    longURL += DUPLICATE;
                    shortUrl = getShortURL(longURL,HashUtils.hashToBase62(longURL));
                }
            }
            //在短长链接MAP对应存储
            Short2Long.put(shortUrl,longURL);
        }
        Long2Short.put(longURL,shortUrl);
        return shortUrl;
    }

}