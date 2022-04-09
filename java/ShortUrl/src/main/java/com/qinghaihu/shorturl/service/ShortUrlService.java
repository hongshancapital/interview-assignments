package com.qinghaihu.shorturl.service;

import com.google.common.hash.HashFunction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortUrlService {

    //短链到长链映射，用于解析短链接
    private final Map<String,String> shortLongMap = new ConcurrentHashMap<>();

    //长链到短链映射,用于重复生成检测
    private final Map<String,String> longShortMap = new ConcurrentHashMap<>();

    //MurmurHash算法生成类
    private final HashFunction hashFunction = Hashing.murmur3_32();

    //hash冲突附加字符串
    @Value("${short.url.conflict_appendix:NULLDUPLICATENULL}")
    private  String CONFLICT_APPENDIX ;


    //64进制编码表
    private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z','_','-'};

    /**
     * 长域名转为短域名,存储并返回转换结果
     * @param longUrl 待转换长域名
     * @return
     */
    public String transferToShortUrl(String longUrl){
        if(StringUtils.isEmpty(longUrl)){
            throw new RuntimeException("待转换的长域名为空!");
        }
        if(longShortMap.containsKey(longUrl)){
            //已存在映射关系，无需重新生成，直接返回
            return longShortMap.get(longUrl);
        }else {
            //基于MurmurHash算法生成
            String shortUrl = "";
            StringBuilder longUrlSb = new StringBuilder(longUrl);
            do {
                HashCode hashCode = hashFunction.hashString(longUrlSb, StandardCharsets.UTF_8);// 采用32位MurmurHash算法，支持数据范围2^32=4294967296
                shortUrl= compressNumber(hashCode.asInt());
                longUrlSb.append(CONFLICT_APPENDIX);
            }while(shortLongMap.containsKey(shortUrl)); //hash冲突检测
            longShortMap.put(longUrl,shortUrl);
            shortLongMap.put(shortUrl,longUrl);
            return shortUrl;
        }
    }

    /**
     * 短域名转为长域名
     * @param shortUrl 待转换短域名
     * @return
     */
    public String transferToLongUrl(String shortUrl){
        if(!shortLongMap.containsKey(shortUrl)){
            throw  new RuntimeException("短域名不存在");
        }
        return shortLongMap.get(shortUrl).replace(CONFLICT_APPENDIX,"");
    }


    /**
     * 64位字符编码    对[0，2^32]范围内数据编码生成的字符长度[1,6]
     * @param number  待编码整数
     * @return
     */
    private  String compressNumber(int number) {
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
