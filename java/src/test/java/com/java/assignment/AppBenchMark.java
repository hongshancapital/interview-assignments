package com.java.assignment;

import com.alibaba.fastjson.JSONObject;
import com.java.assignment.web.controller.ApiOperatorController;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(Threads.MAX)
public class AppBenchMark {

    private static final int SIZE = 100;

    private ApiOperatorController controller;

    private List<JSONObject> list = new LinkedList<>();

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(AppBenchMark.class.getSimpleName())
                .warmupIterations(1) // 预热
                .forks(1)// 创建1个进程来测试
                .threads(10)// 线程数10
                .measurementIterations(5)// 一共测试5轮
//                .measurementTime(TimeValue.seconds(5))// 每轮测试的时长
                .output("D:/Benchmark.log")
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setUp() {
        controller = new ApiOperatorController();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void longToShort() {
        for (int s = 0; s < SIZE; s++) {
            list.add((JSONObject) controller.longToShort("https://echarts.baidu.com/examples/editor.html?c=" + s).getData());
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void shortToLong() {
        for (JSONObject s : list) {
            System.out.print(controller.getLongUrl(s.get("shortUrl").toString(),null));
        }
    }
}