package com.cx.shorturl.app.service.impl;

import com.cx.shorturl.app.data.ShortUrl;
import com.cx.shorturl.app.id.IdGenerator;
import com.cx.shorturl.app.service.ShortUrlService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.*;


public class ShortUrlServiceImpl implements ShortUrlService, InitializingBean {

    private IdGenerator idGenerator;

    public ShortUrlServiceImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Value("${shorturl.cached.max:5000000}")
    private int maxUriCached;

    private ConcurrentMap<String, ShortUrl> shortUrlMap = new ConcurrentHashMap<>();

    private BlockingQueue<String> shortUrlQueue = new LinkedBlockingDeque<>();

    private Cache<String, ShortUrl> cahced;

    private ExecutorService singlePool = Executors.newSingleThreadExecutor();

    @Override
    public String generatorShortUrl(String longUrl) {
        ShortUrl url = null;
        try {
            url = cahced.get(longUrl, () -> {
                if (shortUrlQueue.size() == 0){
                    synchronized (this){
                        if (shortUrlQueue.size() == 0){
                            singlePool.execute(() -> {
                                //预生成1000个
                                for(int i=0; i<1000; i++){
                                    long id = idGenerator.incrId();
                                    String shortStr = toBase62(id);
                                    shortUrlQueue.add(shortStr);
                                }
                            });
                        }
                    }
                }
                String str = shortUrlQueue.take();
                ShortUrl shortUrl = new ShortUrl(str, longUrl);
                shortUrlMap.put(str, shortUrl);
                return shortUrl;
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return url.getShortUrl();
    }

    @Override
    public String getLongUrl(String shortUrl) {
        ShortUrl tinyUrl = shortUrlMap.get(shortUrl);
        if (null == tinyUrl) {
            return null;
        }
        return tinyUrl.getLongUrl();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cahced = CacheBuilder.newBuilder()
                .maximumSize(maxUriCached)
                .removalListener((RemovalListener<String, ShortUrl>) notification -> {
                    ShortUrl shortUrl = notification.getValue();
                    if (null != shortUrl) {
                        shortUrlMap.remove(shortUrl.getShortUrl());
                    }
                })
                .expireAfterWrite(Long.MAX_VALUE, TimeUnit.DAYS).build();
    }

    private static final String str = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 只要num不超过 281474976710656 62的8次方，就可以保证位数在8位
     */
    public String toBase62(long num) {
        StringBuilder sb = new StringBuilder();
        while(num > 0){
            int i = (int) (num % 62);
            sb.append(str.charAt(i));
            num /= 62;
        }
        return sb.reverse().toString();
    }
}
