package com.test.shorturl.service;

import com.test.shorturl.common.exception.ShortUrlException;
import com.test.shorturl.common.result.ResultCodeEnum;
import com.test.shorturl.config.Configer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: liurenyuan
 * @Date: 2021/11/10
 * @Version: 1.0
 */
@Service
public class ShortUrlService {
    private static final Logger logger = LoggerFactory.getLogger(ShortUrlService.class);
    @Resource
    Configer configer;
    public static char[] table = {'P', 'R', 'l', 'x', 'j', 'O', 'z', 'U', '5', '9', 'E', 'n', '6', 'u', '7', 'F', 'v', 'T', '4', 'J', 'q', 'M', 'V', 'b', 'r', 'f', 'd', 'a', 'A', 'K', 'k', 'D', 'o', 'N', 'm', 'y', 'C', '0', '2', 'Q', 'Y', 't', '3', 'I', 'h', 'L', 'Z', '8', 'g', 'G', 'S', 's', 'X', 'i', 'p', 'w', 'B', '1', 'e', 'W', 'c', 'H'};
    private AtomicLong sequence = new AtomicLong(-1l);
    private static final Long MAX_SEQUENCE = (long) Math.pow(62, 8);//62的8次方转换成10进制的最大值，防止递增序列号越界
    private static final Long ZERO = 0L;
    private static ConcurrentHashMap<String, String> longToShortMap = new ConcurrentHashMap<>(1000);//存储原始地址到短地址的映射
    private static ConcurrentHashMap<String, String> shortToLongMap = new ConcurrentHashMap<>(1000);//存储短地址到原始地址的映射,以空间换时间，hashmap时间复杂度O(1),避免短地址通过循环查找长地址导致长时间耗时

    /**
     * 将sequence转换成62进制数据，根据余数定位到table数组中的索引字符 拼接生成短地址
     *
     * @param sequence
     * @return
     */
    public String longTransferToShortString(long sequence) {
        String res = "";
        boolean max = false;
        if (sequence > MAX_SEQUENCE) {
            logger.debug(ResultCodeEnum.EXCEEDS_MAXIMUM_VALUE.message());
            throw new ShortUrlException(ResultCodeEnum.EXCEEDS_MAXIMUM_VALUE, configer.writableStackTrace);
        }
        if (sequence < ZERO) {
            logger.debug(ResultCodeEnum.EXCEEDS_ZERO_VALUE.message());
            throw new ShortUrlException(ResultCodeEnum.EXCEEDS_ZERO_VALUE,configer.writableStackTrace);
        }
        if (sequence == MAX_SEQUENCE) {
            max = true;
        }
        while (sequence >= 62l) {
            res = table[(int) (sequence % 62)] + res;
            sequence = (long) Math.floor(sequence / 62);
        }
        if (!max) {
            res = table[(int) sequence] + res;
        }
        return res;
    }

    /**
     * 将原始地址转换为短地址
     *
     * @param longUrl
     * @return
     */
    public String longToShort(String longUrl) {
        String shortUrl = longToShortMap.get(longUrl);
        if (shortUrl != null) {
            return shortUrl;
        }
        long seq = sequence.incrementAndGet();//取自增序列号
        shortUrl = longTransferToShortString(seq);
        longToShortMap.put(longUrl, shortUrl);//存储长地址和短地址的一一对应关系
        shortToLongMap.put(shortUrl, longUrl);//存短地址和长地址的一一对应关系,以空间换时间，hashmap时间复杂度O(1),避免短地址通过循环查找长地址导致长时间耗时
        logger.debug(shortUrl);
        return shortUrl;
    }

    /**
     * 将短地址转换为原始地址
     *
     * @param shoutUrl
     * @return
     */
    public String shortToLong(String shoutUrl) {
        String longUrl = shortToLongMap.get(shoutUrl);
        if (longUrl != null) {
            return longUrl;
        } else {
            logger.debug(ResultCodeEnum.SHORT_URL_NOT_EXISTS.message());
            throw new ShortUrlException(ResultCodeEnum.SHORT_URL_NOT_EXISTS,configer.writableStackTrace);
        }
    }
    public Map<String,String> count(){
        Map<String,String> map = new HashMap<>();
        map.put("longToShortMap", longToShortMap.size()+"");//此处会有已知的问题，只能返回的最大size为Integer.MAX_VALUE,62的8次方远远超过这个值。虽然返回的最大值有问题，但超过Integer.MAX_VALUE的部分key存进map没有问题的
        map.put("shortToLongMap", shortToLongMap.size()+"");
        return map;
    }
}
