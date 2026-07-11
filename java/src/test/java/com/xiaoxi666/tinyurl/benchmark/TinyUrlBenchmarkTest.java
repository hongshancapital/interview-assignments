package com.xiaoxi666.tinyurl.benchmark;

import com.xiaoxi666.tinyurl.Launcher;
import com.xiaoxi666.tinyurl.controller.TinyUrlController;
import com.xiaoxi666.tinyurl.domain.StoreParam;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Lists;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/17
 * @Version: 1.0
 * @Description: 基准测试。方法级别，简单评估发号器性能。
 */
@BenchmarkMode({Mode.All})
@Warmup(iterations = 2)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
@Threads(150)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class TinyUrlBenchmarkTest {

    private ConfigurableApplicationContext context;
    private TinyUrlController tinyUrlController;

    // 长链接个数
    @Param({"10000"})
    private int longUrlsCnt;

    private List<StoreParam> storeParams = Lists.newArrayList();

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(TinyUrlBenchmarkTest.class.getSimpleName())
                .output("./performancetest/benchmarkTest.log")
                .build();
        Collection<RunResult> results = new Runner(opt).run();
    }

    @Benchmark
    public void store(Blackhole blackhole)  {
        storeParams.forEach(storeParam -> {
            blackhole.consume(tinyUrlController.store(storeParam));
        });
    }

    // Level.Trial：容器只启动一次即可
    @Setup(Level.Trial)
    public void setup() {
        context = SpringApplication.run(Launcher.class);
        tinyUrlController = context.getBean(TinyUrlController.class);

        Random random = new Random();
        String protocol = "https://";
        for (int i = 0; i < longUrlsCnt; ++i) {
            String domain = RandomStringUtils.randomAlphanumeric(random.nextInt(10));
            String path = RandomStringUtils.randomAlphanumeric(random.nextInt(50));
            String longUrl = protocol + domain + "/" + path;
            storeParams.add(new StoreParam(longUrl, random.nextInt(2)));
        }
    }

    @TearDown
    public void tearDown() {
        context.close();
    }
}
