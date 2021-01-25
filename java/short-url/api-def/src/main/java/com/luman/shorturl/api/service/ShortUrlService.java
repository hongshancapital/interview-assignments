package com.luman.shorturl.api.service;


import com.luman.shorturl.api.model.ShortUrl;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ShortUrlService {
    String gen(String url, Integer expire);

    String getUrl(String code);

    void consumerMessage(ConsumerRecord<String, String> record);
}
