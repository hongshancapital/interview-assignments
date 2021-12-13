package com.zxp.demo.service.impl;

import com.zxp.demo.service.ShortUrlChangeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @description: 长链接转短链接实现接口
 * @author: zxp
 * @date: Created in 2021/12/13 12:54
 */
@Service("ShortUrlChangeServiceImpl")
public class ShortUrlChangeServiceImpl implements ShortUrlChangeService {

    @Value("${short.url.prefix}")
    private String shortUrlPrefix;
    @Value("${short.url.timeout}")
    private Long shortUrlTimeout;


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final  String KeyPrefix = "short_url_hash_md5_key_";

    private static final  String shortUrlKeyPrefix = "short_url_hash_key_";

    private static final  String serialNumberKey = "short_url_serial_number";

    private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '-', '_'};


    private static final Logger log = LoggerFactory.getLogger(ShortUrlChangeServiceImpl.class);
    @Override
    public String conversionShortUrl(String longUrl) {

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        final String md5 = DigestUtils.md5Hex(longUrl);
        // 去redis查找此链接是否存在
        String shortUrl = operations.get(KeyPrefix + md5);
       if (StringUtils.isEmpty(shortUrl)){
            synchronized (md5.intern()){
                shortUrl = operations.get(KeyPrefix + md5);
                if (shortUrl == null){
                    Long value = stringRedisTemplate.opsForValue().increment(serialNumberKey, 1);
                    String longString = compressNumber(value);
                    shortUrl = shortUrlPrefix + longString;
                    operations.set(KeyPrefix + md5, shortUrl, shortUrlTimeout, TimeUnit.DAYS);
                    operations.set(shortUrlKeyPrefix + longString, longUrl, shortUrlTimeout, TimeUnit.DAYS);
                }
            }
        }
        log.info("短链接生成成功:{}",shortUrl);
        return shortUrl;

    }




    @Override
    public String conversionLongUrl(String shortUrl) {
        String key="";
        if(StringUtils.isNoneEmpty(shortUrl)){
            key=shortUrl.replace(shortUrlPrefix,"");
        }
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        // 从redis中寻找对应的长域名
        return operations.get(shortUrlKeyPrefix + key);
    }






  private static String compressNumber(long number) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= 6;
        } while (number != 0);

        return new String(buf, charPos, (64 - charPos));

    }


}
