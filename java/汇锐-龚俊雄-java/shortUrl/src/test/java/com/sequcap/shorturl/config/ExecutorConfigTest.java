package com.sequcap.shorturl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfigTest {

    Logger log = LoggerFactory.getLogger(ExecutorConfigTest.class);
	
    @Bean(name ="threadTestExecutor")
    public Executor threadTestExecutor() {
        log.info("start threadTestExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(8);
        //配置最大线程数
        executor.setMaxPoolSize(16);
        //配置队列大小
        executor.setQueueCapacity(999);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("test-thread-executor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
