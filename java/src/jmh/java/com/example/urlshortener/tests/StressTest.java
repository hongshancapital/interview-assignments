package com.example.urlshortener.tests;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.concurrent.TimeUnit;
import static com.example.urlshortener.service.ShortUrlServiceKt.shortenUrl;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(time = 1, iterations = 5)
@Measurement(time = 1, iterations = 100)
@Fork(1)
@State(value = Scope.Benchmark)
public class StressTest {

    @Benchmark
    public String testShorten() {
        return shortenUrl("http://localhost/using-jmh-to-stresstest-shortenUrl-method");
    }

    public static void main(String[] args) {
        Options opts = new OptionsBuilder()
                .include(StressTest.class.getSimpleName())
                .result("shorten_test.json")
                        .resultFormat(ResultFormatType.JSON).build();
        try {
            new Runner(opts).run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }
}
