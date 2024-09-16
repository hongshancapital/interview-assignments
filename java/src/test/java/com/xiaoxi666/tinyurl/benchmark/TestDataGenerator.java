package com.xiaoxi666.tinyurl.benchmark;

import com.xiaoxi666.tinyurl.Launcher;
import com.xiaoxi666.tinyurl.controller.TinyUrlController;
import com.xiaoxi666.tinyurl.domain.Response;
import com.xiaoxi666.tinyurl.domain.StoreParam;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Lists;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/17
 * @Version: 1.0
 * @Description: 利用基准测试的功能生成一批用于压测的数据。
 */
@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 1)
@State(Scope.Benchmark)
public class TestDataGenerator {

    private static final String TINY_URL_PREFIX = "https://xiaoxi666/tinyurl/";

    private ConfigurableApplicationContext context;
    private TinyUrlController tinyUrlController;

    // 长链接个数
    @Param({"1000000"})
    private int longUrlsCnt;

    private List<StoreParam> storeParams = Lists.newArrayList();

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(TestDataGenerator.class.getSimpleName())
                .build();
        Collection<RunResult> results = new Runner(opt).run();
    }

    @Benchmark
    public void store() throws IOException {
        List<String> longUrls = Lists.newArrayList();
        List<String> tinyPaths = Lists.newArrayList();
        storeParams.forEach(storeParam -> {
            Response store = tinyUrlController.store(storeParam);
            if (store.getCode() == 200) {
                longUrls.add(storeParam.getLongUrl());
                tinyPaths.add(((String)store.getData()).substring(TINY_URL_PREFIX.length()));
            }
        });
        Files.write(Paths.get("./performancetest/longUrls.csv"), longUrls);
        Files.write(Paths.get("./performancetest/tinyPaths.csv"), tinyPaths);
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
