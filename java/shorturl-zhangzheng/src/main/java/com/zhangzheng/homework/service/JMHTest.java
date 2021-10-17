package com.zhangzheng.homework.service;

import com.zhangzheng.homework.Application;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/16 下午6:14
 */
@BenchmarkMode({Mode.Throughput,Mode.AverageTime})// 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.SECONDS)// 输出结果的时间粒度为豪秒
@State(Scope.Benchmark) // 每个测试线程一个实例
@Slf4j
public class JMHTest {

    private ConfigurableApplicationContext context;
    private ConvertService convertService;

    private static final List<String> shortUrlList = new ArrayList<>();

    @Setup(Level.Trial)
    public void init(){
        context = SpringApplication.run(Application.class);
        convertService = context.getBean(ConvertService.class);
//        for(int i=0;i<1000;i++){
//            String longUrl = "http://www.testshorturl.com/a/abc";
//            longUrl = longUrl.concat(String.valueOf(System.currentTimeMillis()));
//            String shortUrl = convertService.generate(longUrl);
//            shortUrlList.add(shortUrl);
//        }

    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        String longUrl = "http://www.testshorturl.com/a/abc";

        public String getLongUrl(String tail){
            return longUrl.concat(tail);
        }
    }

    @Benchmark
    public void testGenerate(BenchmarkState state){
        String longUrl = state.getLongUrl(String.valueOf(System.currentTimeMillis()));
        String shortUrl = convertService.generate(longUrl);
        shortUrlList.add(shortUrl);
    }
    public void testRevert(){
        if(shortUrlList != null && !shortUrlList.isEmpty()){
            convertService.revertUrl(shortUrlList.get(new Random().nextInt(shortUrlList.size()-1)));
        }
    }

    public static void main(String[] args) {
        Options options = new OptionsBuilder().include(JMHTest.class.getName())
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.seconds(1))
                .threads(Runtime.getRuntime().availableProcessors()+1)
                .forks(1)
                .syncIterations(true)
                .build();
        try {
            new Runner(options).run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }
}
