package com.sequoia.jmh;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Weigher;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * CaffeineCacheBenchMark
 *
 * @author KVLT
 * @date 2022-04-10.
 */
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class CaffeineCacheBenchMark {

    private static final long TINY_STORE_MAX_WEIGHT = 1024_000_000L;

    private static final Cache<String, String> TINY_ORIGIN_STORE =
            Caffeine.newBuilder()
                    .maximumWeight(TINY_STORE_MAX_WEIGHT)
                    .weigher((Weigher<String, String>)(k, v) -> (k.length() + v.length()) * 2)
                    .build();

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CaffeineCacheBenchMark.class.getSimpleName())
                .result("result.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

    @Benchmark
    public void testPut() {
        String key = UUID.randomUUID().toString();
        String value = key + "_";
        TINY_ORIGIN_STORE.put(key, value);
    }

    @Benchmark
    public void testGetIfPresent() {
        TINY_ORIGIN_STORE.getIfPresent(UUID.randomUUID().toString());
    }

}
