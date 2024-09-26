package com.example.shorturl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author yyp
 * @date 2022/1/16 19:54
 */
@Configuration(value = "generator")
public class GeneratorConfig {
    /**
     * 62^8 = 218340105584896 (218万亿)
     * 按
     */
    @Value("${generate.machine.index:3}")
    public Long idx;
    @Value("${generate.machine.shard:10}")
    public Integer shardCount;

    @Value("${auto.increment.step:10}")
    public Integer step;

    @Value("${queue.size:10}")
    public Integer queueSize;

    @Value("${host:http://st.cn}")
    public String shortHost;

    @Value("${queue.length:100}")
    public Integer queueLen;

    @Value("${shorturl.length:8}")
    public Integer urlLength;

    @Value("${shorturl.cache.seconds:60}")
    public Long cacheSeconds;
}
