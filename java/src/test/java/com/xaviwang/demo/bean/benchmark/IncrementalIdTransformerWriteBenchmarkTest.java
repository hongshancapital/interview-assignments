package com.xaviwang.demo.bean.benchmark;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.xaviwang.demo.bean.IncrementalIdTransformer;
import com.xaviwang.demo.bean.Transformer;

@State(Scope.Thread)
public class IncrementalIdTransformerWriteBenchmarkTest {

	public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder().include(IncrementalIdTransformerWriteBenchmarkTest.class.getSimpleName())
				.forks(1)
				.build();
		new Runner(opt).run();
	}
	
	@Benchmark
	//@BenchmarkMode(Mode.AverageTime)
	@BenchmarkMode(Mode.AverageTime)
	@Warmup(iterations = 3)
	@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.NANOSECONDS)
	@Threads(1)
	@Fork(1)
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	public void test() throws InterruptedException {
		Transformer incrementalIdTransformer = new IncrementalIdTransformer();
		incrementalIdTransformer.transformFromOriginalUrlToTinyUrl("www.test.com");
	}

}
