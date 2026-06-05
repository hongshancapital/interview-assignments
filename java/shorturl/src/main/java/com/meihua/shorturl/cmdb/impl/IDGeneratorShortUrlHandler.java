package com.meihua.shorturl.cmdb.impl;

import com.meihua.shorturl.cmdb.ShortUrlGenerator;
import com.meihua.shorturl.util.IDGeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  <p>
 *   发号器模式
 *  </p>
 * @author meihua
 * @version 1.0
 * @date 2021/10/12
 */
public class IDGeneratorShortUrlHandler implements ShortUrlGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IDGeneratorShortUrlHandler.class);

    private static ConcurrentHashMap<String,String> map;

    /**实例号码段限制*/
    private static int maxId;
    /**实例号码段限制*/
    private static int startId;

    private static AtomicInteger increasingId;

    /** 短域名 8位限制 超出此范围ID 8位将无法表示**/
    public static  Long limit ;

    @Value("${url.digit-limit:8}")
    public  void setLimit(int limit) {
        IDGeneratorShortUrlHandler.limit = (long)Math.pow(62,limit+1);
    }

    @Value("${url.start-id:8848}")
    public  void setStartId(int startId) {
        IDGeneratorShortUrlHandler.startId = startId;
    }
    @Value("${url.max-count:512}")
    public  void setMaxId(int maxCount) {
        IDGeneratorShortUrlHandler.maxId = maxCount*1024;
    }

    @Override
    public void destroy(){
        //可以考虑持久化操作
        logger.warn("IDGeneratorShortUrlHandler destroy ! ");
    }
    @Override
    public void init(){
        //可以考虑从持久化的地方读取数据
        map= new ConcurrentHashMap<>(maxId);
        increasingId = new AtomicInteger(IDGeneratorShortUrlHandler.startId);
        IDGeneratorShortUrlHandler.maxId+=startId;
        logger.info("IDGeneratorShortUrlHandler init done ! startId :{} maxCount: {}  limit :{}",increasingId.get(),maxId,limit);
    }

    @Override
    public  String getValue(String key){
        return map.get(key);
    }

    @Override
    public  String put(String url){
        int id = increasingId.incrementAndGet();
        if ( id>=limit || id> IDGeneratorShortUrlHandler.maxId ){
            logger.error("id : {}  maxCount: {}  limit :{}",increasingId.get(), maxId, limit);
            throw new  RuntimeException(" maximum limit exceeded ");
        }
        String shortUrl = IDGeneratorUtils.getId(id);
        map.put(shortUrl,url);
        //可以持久化
        return shortUrl;
    }
}
