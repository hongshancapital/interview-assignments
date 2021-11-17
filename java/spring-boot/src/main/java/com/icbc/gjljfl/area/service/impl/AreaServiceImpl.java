package com.icbc.gjljfl.area.service.impl;
import com.icbc.gjljfl.area.service.AreaService;
import com.icbc.gjljfl.common.ResponseEntity;
import com.icbc.gjljfl.common.enums.ErrorEnum;
import com.icbc.gjljfl.mapper.AreaMapper;
import com.icbc.gjljfl.util.ResponseUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    private static Logger log = LoggerFactory.getLogger(AreaServiceImpl.class);
    ConcurrentHashMap urlMap = new ConcurrentHashMap();
    @Resource
    private AreaMapper areaDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private static final  String KeyPrefix = "short_url_hash_md5_key_";
    private static final  String shortUrlKeyPrefix = "short_url_hash_key_";
    private static final  String serialNumberKey = "short_url_serial_number";
    private String shortUrlPrefix = "short_url";
    private Long shortUrlTimeout = 1000L;
    private static final String SHORT_URL_PREFIX = "/t/";
    private static final  String SHORT_URL_KEY_PREFIX = "short_url_hash_key_";
    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '-', '_'};
    //短域名存储接口：接受长域名信息，返回短域名信息
    @Override
    public ResponseEntity saveUrl(String url) {
        String url_short="";
        try{
            if (StringUtils.isNotBlank(url) && url.length()<=16){
                ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
                final String md5 = DigestUtils.md5Hex(url);
                String shortUrl = operations.get(KeyPrefix + md5);
                if (shortUrl == null) {
                    synchronized (md5.intern()) {
                        shortUrl = operations.get(KeyPrefix + md5);
                        if (shortUrl == null) {
                            Long value = stringRedisTemplate.opsForValue().increment(serialNumberKey, 1);
                            String longString = compressNumber(value);
                            shortUrl = shortUrlPrefix + longString;
                            operations.set(KeyPrefix + md5, shortUrl, shortUrlTimeout, TimeUnit.DAYS);
                            operations.set(shortUrlKeyPrefix + longString, url, shortUrlTimeout, TimeUnit.DAYS);
                            urlMap.put(KeyPrefix + md5, shortUrl);
                            urlMap.put(shortUrlKeyPrefix + longString, url);
                            url_short = shortUrl;
                        }
                    }
                }else {
                    url_short = shortUrl;
                }
                return ResponseUtils.toSuccess(url_short);
            }else {
                return ResponseUtils.toFail(ErrorEnum.PARAM_ERROR);
            }
        }catch (Exception e){
            log.error("saveUrl---",e);
            return ResponseUtils.toFail(ErrorEnum.FAIL);
        }
    }
    //短域名读取接口：接受短域名信息，返回长域名信息
    @Override
    public ResponseEntity readUrl(String url) {
        String url_long ="";
        try{
            if (StringUtils.isNotBlank(url) && url.length()<=16) {
                //String longUrl = stringRedisTemplate.opsForValue().get(SHORT_URL_KEY_PREFIX + url.substring(url.lastIndexOf("/") + 1));
                String longUrl = stringRedisTemplate.opsForValue().get(SHORT_URL_KEY_PREFIX + url.substring(url.length() - 1));
                synchronized (AreaServiceImpl.class) {
                    if (StringUtils.isNotBlank(longUrl)) {
                        url_long = longUrl;
                    } else {
                        Long value = stringRedisTemplate.opsForValue().increment(serialNumberKey, 1);
                        String longString = compressNumber(value);
                        url_long = urlMap.get(shortUrlKeyPrefix + longString).toString();
                    }
                }
            }else {
                return ResponseUtils.toFail(ErrorEnum.PARAM_ERROR);
            }
            return ResponseUtils.toSuccess(url_long);
        }catch (Exception e){
            log.error("readUrl---",e);
            return ResponseUtils.toFail(ErrorEnum.FAIL);
        }
    }
    public static String compressNumber(long number) {
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
