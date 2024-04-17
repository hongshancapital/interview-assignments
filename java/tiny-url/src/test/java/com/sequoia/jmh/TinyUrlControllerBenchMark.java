package com.sequoia.jmh;

import com.sequoia.TinyUrlApplication;
import com.sequoia.app.TinyUrlController;
import com.sequoia.domain.UrlRequest;
import com.sequoia.infrastructure.util.HexUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TinyUrlControllerBenchMark
 *
 * @author KVLT
 * @date 2022-04-10.
 */
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(5)
@Fork(1)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class TinyUrlControllerBenchMark {

    private static final String tinyUrlPrefix = "http://seq.cn/";

    // 指定全局变量
    private volatile ConfigurableApplicationContext context;
    private TinyUrlController tinyUrlController;

    AtomicInteger port = new AtomicInteger(10000);

    /**
     * Setup 初始化容器的时候只执行一次
     */
    @Setup(Level.Trial)
    public synchronized void init() {
        // springboot 使用随机port
        System.setProperty("server.port", (10000 + ThreadLocalRandom.current().nextInt(10000)) + "");
        if (null == context) {
            context = SpringApplication.run(TinyUrlApplication.class, "");
        }
        tinyUrlController = context.getBean(TinyUrlController.class);
    }

    @TearDown
    public void destory() {
        context.close();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(TinyUrlControllerBenchMark.class.getName()+".*")
//                .warmupIterations(2)
//                .measurementIterations(5)
//                .forks(1)
                .shouldDoGC(true)
                .result("result.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

    @Benchmark
    public void testGetTinyUrl(TinyUrlControllerBenchMark state, Blackhole bh) {
        UrlRequest urlRequest = new UrlRequest(tinyUrlPrefix + UUID.randomUUID().toString());
        tinyUrlController.getTinyUrl(urlRequest);
    }

    @Benchmark
    public void testGetOriginUrl(TinyUrlControllerBenchMark state, Blackhole bh) {
        String url = tinyUrlPrefix + "123dFg" + HexUtil.getRandomHex62Char();
        UrlRequest urlRequest = new UrlRequest(url);
        tinyUrlController.getOriginUrl(urlRequest);
    }

    @Benchmark
    public void testNull(TinyUrlControllerBenchMark state, Blackhole bh) {
        tinyUrlController.test();
    }

}
