package com.zjm.sdct_work.benchmark;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zjm.sdct_work.ShortcutApplication;
import com.zjm.sdct_work.controller.ShortcutController;
import com.zjm.sdct_work.controller.api.ShortcutPostReq;
import com.zjm.sdct_work.service.ShortcutService;
import com.zjm.sdct_work.util.ShortcutUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Author:   billzzzhang
 * Date:     2022/3/20 下午4:03
 * Desc:
 */
@State(Scope.Benchmark)
public class JMHBenchmark {

    Map<String, String> map = new ConcurrentHashMap<>();

    private Cache<String, String> shortCut2UrlMap;


    private ConfigurableApplicationContext context;

    private ShortcutService shortcutService;
    private ShortcutController shortcutController;
    private ShortcutUtil shortcutUtil;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }




    @Setup
    public void init() {
        context = SpringApplication.run(ShortcutApplication.class);

        shortcutService = context.getBean(ShortcutService.class);
        shortcutController = context.getBean(ShortcutController.class);
        shortcutUtil = context.getBean(ShortcutUtil.class);
        shortCut2UrlMap = Caffeine.newBuilder().initialCapacity(0).maximumSize(1000000).build();
    }


    //测试短域名生成性能
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(100)
    @Warmup(iterations = 5, time = 2)
    @Measurement(iterations = 20, time = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureShortcutThroughput() throws InterruptedException {
        shortcutUtil.generatorRandomStr();
    }

    //测试生成后put到ConcurrentHashMap
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(100)
    @Warmup(iterations = 5, time = 2)
    @Measurement(iterations = 20, time = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureGenerateAndPut() throws InterruptedException {
        String url = UUID.randomUUID().toString();
        String id = shortcutUtil.generatorRandomStr();
        String result = map.putIfAbsent(url, id);
    }

    //测试生成后put到caffeine
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(100)
    @Warmup(iterations = 5, time = 2)
    @Measurement(iterations = 20, time = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureGenerateAndPutByCache() throws InterruptedException {
        String url = UUID.randomUUID().toString();
        String id = shortcutUtil.generatorRandomStr();
        shortCut2UrlMap.put(url,id);
    }

    //测试service层性能
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(100)
    @Warmup(iterations = 5, time = 2)
    @Measurement(iterations = 20, time = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureService() throws InterruptedException {
        String url = UUID.randomUUID().toString();
        shortcutService.createShortcut(url);
    }


    //测试controller层性能
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(100)
    @Warmup(iterations = 5, time = 2)
    @Measurement(iterations = 20, time = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureController() throws InterruptedException {
        String url = "https://www."+ UUID.randomUUID().toString().replace("-","").substring(0,10) + ".com";
        shortcutController.createShortcut(new ShortcutPostReq(url),"abcdefgh");
    }


}
