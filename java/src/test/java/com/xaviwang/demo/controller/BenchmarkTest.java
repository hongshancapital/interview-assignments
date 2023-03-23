package com.xaviwang.demo.controller;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.xaviwang.demo.TinyUrlApplication;
import com.xaviwang.demo.bean.IncrementalIdTransformer;
import com.xaviwang.demo.bean.Transformer;

public class BenchmarkTest {
	/*public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder().include(BenchmarkTest.class.getSimpleName())
				.forks(1)
				.build();
		new Runner(opt).run();
	}*/
	/*
	private ConfigurableApplicationContext context;
	private HelloController controller;
	
	@Setup
	public void init() {
		this.context = SpringApplication.run(SpringBootDemoApplication.class);
		this.controller = context.getBean(HelloController.class);
		//System.out.print("Test");
	}
	
	@TearDown
	public void down() {
		//System.out.print("Test");
		context.close();
	}
	*/
	/*@Benchmark
	//@BenchmarkMode(Mode.AverageTime)
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 3)
	@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.NANOSECONDS)
	@Threads(1)
	@Fork(1)
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	public void test() throws InterruptedException {
		//Thread.sleep(1000);
		Transformer incrementalIdTransformer = new IncrementalIdTransformer();
		incrementalIdTransformer.transformFromLongUrlToShortUrl("www.test.com");
	}*/
}
