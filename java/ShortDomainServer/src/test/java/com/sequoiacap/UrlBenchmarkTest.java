package com.sequoiacap;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;

import com.sequoiacap.util.HostUtil;
import com.sequoiacap.util.HttpClientUtil;
import com.sequoiacap.util.NumericConvertUtil;

/**
 * 
 * @author zoubin
 */
@State(Scope.Benchmark)
public class UrlBenchmarkTest {

	private final List<String> clusterBasePath = Lists.newArrayList();
	int size = 1000000;
	
	@Before
	public void setUp() {
		String ip = HostUtil.getLocalAddress();
		System.setProperty("serverCluster", String.format("%s:%d,%s:%d,%s:%d", ip, 8981, ip, 8982, ip, 8983));
		for(String addr: StringUtils.split(System.getProperty("serverCluster"), ',')) {
			clusterBasePath.add(String.format("http://%s/urlParse", addr));
		}
		System.setProperty("serverPort", "8981");
		SpringApplication.run(AppServerApplication.class, new String[] {});
		System.setProperty("serverPort", "8982");
		SpringApplication.run(AppServerApplication.class, new String[] {});
		System.setProperty("serverPort", "8983");
		SpringApplication.run(AppServerApplication.class, new String[] {});
	}
	
	@After
	public void clear() {
		System.clearProperty("serverCluster");
		System.clearProperty("serverPort");
	}
	
	@Test
	public void launchBenchmark() throws Exception {
		// for enhance jacoco recover rate
		test(10);
		
		// for benchmark
		Options opt = new OptionsBuilder()
				// Specify which benchmarks to run.
				// You can be more specific if you'd like to run only one benchmark per test.
				.include(this.getClass().getName() + ".*")
				// Set the following options as needed
				.shouldFailOnError(true).shouldDoGC(true)
				.result("jmh-benchmark.json")
			    .resultFormat(ResultFormatType.JSON)
				.jvmArgs("-Xmx3g","-Xmn1g","-Xms3g","-XX:+UnlockDiagnosticVMOptions")
				.build();

		new Runner(opt).run();
	}
	
    @Benchmark
    @BenchmarkMode(Mode.All)
    @Warmup(iterations = 0)
    @Measurement(iterations = 3, time = 30, timeUnit = TimeUnit.SECONDS)
    @Threads(32)
    @Fork(1)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void testBenchmark() {
		test(size);
	}

	private void test(int size) {
		for(int i=0; i < size; i++) {
			store(String.valueOf(UUID.randomUUID().getMostSignificantBits()));
		}
		for(int i=0; i < size; i++) {
			get(NumericConvertUtil.toOtherNumberSystem(i, 62));
		}
	}

	private void get(String shortUrl) {
		for(String basePath: clusterBasePath) {
			try {
				HttpClientUtil.getUrl(String.format("%s/%s", basePath, shortUrl));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}		
	}

	private void store(String longUrl) {
		for(String basePath: clusterBasePath) {
			try {
				HttpClientUtil.postUrl(basePath, longUrl);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
