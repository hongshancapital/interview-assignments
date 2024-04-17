package com.bingl.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.bingl.web.persist.ShortUrlEntity;
import com.bingl.web.service.ShortUrlService;
import com.bingl.web.util.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private static Logger logger= LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    private static final String CODES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static ConcurrentHashMap<String,ShortUrlEntity> entityMap=new ConcurrentHashMap<>();

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     */
    public String  saveShortUrl(String longUrl){

        String md5Url= DigestUtils.md5DigestAsHex(longUrl.getBytes());//md5减少字符的长度，不让它太长 32位
        //需要将一个长网址转化为固定的8段,在将每段小字符转化为一个数字，最后将数字和CODES hash对应上
        int x=md5Url.length()/7;
        int last=md5Url.length()-x*7;

        //logger.info(longUrl+"---------"+last);
        ShortUrlEntity shortUrlEntity=new ShortUrlEntity();
        shortUrlEntity.setLongUrl(longUrl)
                .setMd5Url(md5Url)
                .setCharIntList(new ArrayList<>());


        StringBuffer shortUrl=new StringBuffer();

        for(int i=0;i<7;i++){
            String charStr=md5Url.substring(i*x,(i+1)*x);

            byte[] bytes=charStr.getBytes();

            int intValue= ByteUtil.byteToIntHighHead(bytes,0);
            shortUrl.append(CODES.charAt(intValue%CODES.length()));
            shortUrlEntity.getCharIntList().add(intValue);
        }

        //除不尽，剩余字符处理，作为第8号字符,由于md5是32位除以7永远余4
        String charStr=md5Url.substring(md5Url.length()-last);
        System.out.println(charStr);
        int intValue=ByteUtil.byteToIntHighHead(charStr.getBytes(),0);
        shortUrl.append(CODES.charAt(intValue%CODES.length()));
        shortUrlEntity.getCharIntList().add(intValue);

        shortUrlEntity.setShortUrl(shortUrl.toString());
        shortUrlEntity.setCreateTime(new Date());
        logger.info("短域名生成成功:"+JSON.toJSONString(shortUrlEntity));

        //持久化
        //映射数据存储在JVM内存即可，防止内存溢出；
        entityMap.put(shortUrl.toString(),shortUrlEntity);

        return shortUrl.toString();
    }

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     * @param shortUrl
     * @return
     */
    public String readLongUrl(String shortUrl) throws Exception{

        ShortUrlEntity shortUrlEntity=entityMap.get(shortUrl);
        if(shortUrlEntity == null){
            throw new Exception("未知的短域名"+shortUrl);
        }

        return shortUrlEntity.getLongUrl();
    }



}
