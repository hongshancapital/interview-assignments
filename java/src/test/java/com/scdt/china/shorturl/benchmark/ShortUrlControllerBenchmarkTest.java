package com.scdt.china.shorturl.benchmark;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 基准测试
 */
@EnabledIf("enable")
public class ShortUrlControllerBenchmarkTest {

    private static final Logger LOG = LoggerFactory.getLogger(ShortUrlControllerBenchmarkTest.class);

    public static boolean enable() {
        boolean benchmark = Objects.equals(System.getProperty("benchmark"), "true");
        if (!benchmark) {
            LOG.info("基准测试默认禁用，如需启用请指定JVM属性benchmark=true");
        }
        return benchmark;
    }

    @Test
    @DisplayName("基准测试：吞吐量")
    public void throughput() throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ShortUrlControllerBenchmark.class.getSimpleName())
                .mode(Mode.Throughput)
                .forks(1)
                .warmupIterations(1)
                .measurementIterations(5).measurementTime(TimeValue.seconds(30))
                .threads(12)
                .timeUnit(TimeUnit.MILLISECONDS)
                .output("./doc/ThroughputBenchmark.log")
                .build();
        new Runner(options).run();
    }

    @Test
    @DisplayName("基准测试：平均响应时间")
    public void averageTime() throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ShortUrlControllerBenchmark.class.getSimpleName())
                .mode(Mode.AverageTime)
                .forks(1)
                .warmupIterations(1)
                .measurementIterations(5).measurementTime(TimeValue.seconds(30))
                .threads(12)
                .timeUnit(TimeUnit.NANOSECONDS)
                .output("./doc/AverageTimeBenchmark.log")
                .build();
        new Runner(options).run();
    }

    @Test
    @DisplayName("基准测试：采样响应时间")
    public void sampleTime() throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ShortUrlControllerBenchmark.class.getSimpleName())
                .mode(Mode.SampleTime)
                .forks(1)
                .warmupIterations(1)
                .measurementIterations(5).measurementTime(TimeValue.seconds(30))
                .threads(12)
                .timeUnit(TimeUnit.NANOSECONDS)
                .output("./doc/SampleTimeBenchmark.log")
                .build();
        new Runner(options).run();
    }

}