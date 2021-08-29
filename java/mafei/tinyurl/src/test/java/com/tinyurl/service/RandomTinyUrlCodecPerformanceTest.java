package com.tinyurl.service;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
@Fork(5)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3,time = 2)
@Measurement(iterations = 5,time = 10)
public class RandomTinyUrlCodecPerformanceTest {
    RandomTinyUrlCodec randomTinyUrlCodec = new RandomTinyUrlCodec();
    String url = "https://www.example.com/";

    @Benchmark
    public void encode() {
        randomTinyUrlCodec.encode(url);
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RandomTinyUrlCodecPerformanceTest.class.getSimpleName())
                .measurementIterations(2)
                .threads(5)
                .build();
        new Runner(opt).run();
    }
}
