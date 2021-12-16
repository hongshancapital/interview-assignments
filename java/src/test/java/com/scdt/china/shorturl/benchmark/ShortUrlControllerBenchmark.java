package com.scdt.china.shorturl.benchmark;

import com.scdt.china.shorturl.ShortUrlApplication;
import com.scdt.china.shorturl.controller.ShortUrlController;
import com.scdt.china.shorturl.service.ShortUrlService;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Random;

/**
 * 基准测试用例
 */
@State(Scope.Benchmark)
public class ShortUrlControllerBenchmark {

    private ShortUrlController shortUrlController;
    private ConfigurableApplicationContext applicationContext;
    private String urlCode;

    @Setup
    public void init() {
        applicationContext = new SpringApplication(ShortUrlApplication.class).run();
        shortUrlController = applicationContext.getBean(ShortUrlController.class);

        ShortUrlService shortUrlService = applicationContext.getBean(ShortUrlService.class);
        Random random = new Random();
        urlCode = shortUrlService.generate("https://www.google.com?t=" + random.nextLong());
    }

    @TearDown
    public void destroy() {
        applicationContext.close();
    }

    @Benchmark
    public void generate() {
        shortUrlController.generateShortUrl("https://www.baidu.com");
    }

    @Benchmark
    public void expand() {
        shortUrlController.expandShortUrl(urlCode);
    }

}
