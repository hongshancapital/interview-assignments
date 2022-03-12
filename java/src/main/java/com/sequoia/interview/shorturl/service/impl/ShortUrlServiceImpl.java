package com.sequoia.interview.shorturl.service.impl;

import java.util.concurrent.locks.ReentrantLock;

import com.sequoia.interview.shorturl.service.ShortUrlService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * 短地址服务实现类
 *
 * @author james xu <james4xu@aliyun.com>
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private static final Logger logger = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    @Value("${app.start}")
    private String start;

    @Value("${app.end}")
    private String end;

    @Value("${app.seg_len}")
    private Integer seg_len;

    @Autowired
    private StringRedisTemplate redis;

    // 固定短地址为8位，采用62进制则一共可用62^8个短网址，
    private final int MAX_LEN = 8;
    private final String CUR_VALUE_KEY = "curValue";
    private final String URL_KEY = "url";
    private final String SHORT_URL_KEY = "shortUrl";

    private char[] curValue;

    private ReentrantLock lock = new ReentrantLock();


    public void init() {
        try{
            //redis.setEnableTransactionSupport(true);
            if(redis.hasKey(this.CUR_VALUE_KEY)){
                //从 redis中取出当前值
                this.curValue = redis.opsForValue().get(this.CUR_VALUE_KEY).toCharArray();
            }else{
                //redis中没有，则从start开始
                this.curValue=start.toCharArray();
            }
        } catch (Exception e) {
            //redis没有启动时，出异常
            logger.error("redis运行异常！请及时检查redis是否启动．");
        }
    }

    @Override
    public void setCurValue(String curValue) {
        // 为方便测试，curValue传入。
        if(curValue==null){
            this.curValue=null;
        }else {
            this.curValue = curValue.toCharArray();
        }
    }

    @Override
    public String genShortUrl(String url) {
        String shortUrl = "";
        if (this.curValue==null) {
            init();
        }
        //step1 查看是否已经生成过此url的短地址
        if (this.redis.opsForHash().hasKey(this.URL_KEY, url)) {
            shortUrl = (String) this.redis.opsForHash().get(this.URL_KEY, url);
            return shortUrl;
        }
        String old=String.valueOf(curValue);
        this.lock.lock();
        try {

            //step2 如果不存在，则生成新的shortUrl
            shortUrl = String.copyValueOf(this.plusOne(curValue));
            //step3 保存key-value关系
            //TODO 此处可优化，将curValue，url，shortUrl放入先进先出队列，然后另一个独立线程负责保存到redis中.更多信息参见ＲＥＡＤＭＥ.md
            //保存redis中curValue最新
            this.redis.opsForValue().set(this.CUR_VALUE_KEY, shortUrl);
            //保存url-shortUrl关系
            this.redis.opsForHash().put(this.URL_KEY, url, shortUrl);
            //保存short-url关系
            this.redis.opsForHash().put(this.SHORT_URL_KEY, shortUrl, url);
            //this.redis.exec();
        } catch (Exception e) {
            //如过发生异常，如redis无法链接，则恢复原有curValue值
            //logger.warn("redis保存失败： curValue=" + curValue + " , url=" + url + " , shortUrl=" + shortUrl);
            curValue = old.toCharArray();
        } finally {
            lock.unlock();
        }

        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {

        String url = "";
        //try {
        url = (String) redis.opsForHash().get(this.SHORT_URL_KEY, shortUrl);
        //} catch (Exception e) {
        //    logger.warn("getUrl，redis访问失败： shortUrl=" + shortUrl);
        //}
        return url;
    }

    /**
     * 固定8位长度，实现62进制+1，当=end时，从start开始重新计数
     *
     * @param curValue, 为方便
     * @return
     */
    @Override
    public char[] plusOne(char[] curValue) {
        //
        // 已经计数到end，则重start开始重新计数
        if (end.compareTo(String.valueOf(curValue)) == 0) {
            // rule: 如果curValue是end，则重新从start开始此段计数
            return start.toCharArray();
        }

        // 为避免过多的比较及数制转换，采用下面方法处理62进制+1
        char curCh;
        int i = MAX_LEN - 1; // 从最低位开始加
        for (; i >= seg_len; i--) {
            // 0-9A-Za-z: 48-57,65-90,97-122
            curCh = curValue[i];
            if (curCh == 'z') {
                // nextChIdx = 48;
                curValue[i] = '0';
            } else if (curCh == '9') {
                // nextChIdx = 65;
                curValue[i] = 'A';
                break;
            } else if (curCh == 'Z') { // 为字母Z
                // nextChIdx = 97; // Z+1 为a
                curValue[i] = 'a';
                break;
            } else {
                curValue[i] = (char) ((int) curCh + 1);
                break;
            }
        }
        return curValue;

    }

}
