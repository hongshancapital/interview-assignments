package com.luman.shorturl.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.luman.shorturl.api.cache.ShortUrlCache;
import com.luman.shorturl.api.dao.ShortUrlDAO;
import com.luman.shorturl.api.model.ShortUrl;
import com.luman.shorturl.api.service.ShortUrlService;
import com.luman.shorturl.common.util.Radix62;
import com.luman.shorturl.uid.UidGenerator;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import javax.websocket.SendResult;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Value("${domain}")
    private String domain;
    @Resource
    private UidGenerator uidGenerator;
    @Autowired
    ShortUrlCache cache;
    @Autowired
    private ShortUrlDAO shortUrlDAO;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public String gen(String url, Integer expire) {
        Date now = Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.of("+8")));

        Date expireTime = null;
        if(expire != null){
            expireTime=DateUtils.addDays(now,expire);
        }
        String code;
        ShortUrl shortUrl;
        long begin = System.currentTimeMillis();
        if((code=cache.getShortUrl(url,expireTime))==null){
            long uid = uidGenerator.getUID();
            shortUrl = new ShortUrl();
            shortUrl.setId(uid);
            shortUrl.setUrl(url);
            shortUrl.setExpire(expireTime);
            shortUrl.setCode((code=Radix62.to62Radix(uid)));
            shortUrl.setCreateTime(new Date());
            cache.save(shortUrl);
            shortUrlDAO.insert(shortUrl);
            try {
                kafkaTemplate.send("short-url",new ObjectMapper().writeValueAsString(shortUrl));
            } catch (JsonProcessingException e) {
            }
        }
        log.info("gen code:{}",System.currentTimeMillis()-begin);
        return domain+code;
    }

    @Override
    public String getUrl(String code) {
        ShortUrl shortUrl = cache.getUrl(code);
        if(shortUrl==null||(shortUrl.getExpire()!=null && shortUrl.getExpire().getTime()<System.currentTimeMillis())){
            return null;
        }
        return shortUrl.getUrl();
    }

    private static final Logger log = LoggerFactory.getLogger(ShortUrlServiceImpl.class);
    @KafkaListener(topics = {"short-url"})
    @Override
    public void consumerMessage(ConsumerRecord<String, String> record) {
        try {
            ShortUrl shortUrl = new ObjectMapper().readValue(record.value(),ShortUrl.class);
            cache.save(shortUrl);
        } catch (IOException e) {
            log.error("consumerMessage error:",e);
        }
        log.info("consumerMessage:{}",record.value());
    }

}
