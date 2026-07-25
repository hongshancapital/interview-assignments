package com.scdt.shorturl.serviceimpl;

import com.google.common.collect.Maps;
import com.scdt.shorturl.service.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 短URL服务
 * @author niuyi
 * @since  2021-12-10
 */
@Service("defaultShortUrlService")
public class ShortUrlServiceImpl implements ShortUrlService {

    private static final Logger log = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    private static final long MAX=218340105584895L;
    private static final int MAX_URL_LENGTH=256;
    private static final AtomicLong counter=new AtomicLong(0);



    private static final char[] toBase64URL = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    };

    private static final Map<String,String> urlMap= Maps.newConcurrentMap();

    /**
     * 长Url变短
     * @param longUrl 长url
     * @return 短URL
     */
    @Override
    public  String toShortURL(String longUrl) {
        if (!StringUtils.isEmpty(longUrl)){
            if(longUrl.length()>MAX_URL_LENGTH){
                IllegalArgumentException ep=new IllegalArgumentException("URL过长");
                log.error("URL过长",ep);
                throw ep;
            }
            long number =counter.getAndIncrement();
            if(number>=MAX){
                RuntimeException ep=new RuntimeException("达到最大生成数量");
                log.error("达到最大生成数量",ep);
                throw ep;
            }
            char[] buf = new char[64];
            int charPos = 64;
            int radix = 1 << 6;
            long mask = radix - 1;
            do {
                buf[--charPos] = toBase64URL[(int) (number & mask)];
                number >>>= 6;
            } while (number != 0);
            String shortUrl= new String(buf, charPos, (64 - charPos));

            urlMap.put(shortUrl,longUrl);
            return shortUrl;
        }
        IllegalArgumentException ep=new IllegalArgumentException("longUrl为空");
        log.error("longUrl为空",ep);
        throw  ep;
    }

    /**
     * 查找短URL对于的长URL
     * @param shortUrl 短URL
     * @return 长URL
     */
    @Override
    public String toLongUrl(String shortUrl) {
        String longUrl=urlMap.get(shortUrl);
        if (longUrl == null){
            throw  new NullPointerException("URL为空");
        }
        return longUrl;
    }

}