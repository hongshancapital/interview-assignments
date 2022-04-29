package com.youming.sequoia.sdn.apipublic.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * spring async调用的线程池配置 如果当前线程数少于CorePoolSize，那么即使线程都是闲置的，也必须新建线程来处理任务
 * 如果线程数大于等于CorePoolSize，则任务优先放入缓冲队列QueueCapacity，如果缓冲队列满了则新建线程，如果线程大于MaxPoolSize，则拒绝任务并抛出异常
 * 异常由AsyncUncaughtExceptionHandler处理
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);

    @Override
    public Executor getAsyncExecutor() {
        // TODO Auto-generated method stub
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1); // 当前线程数
        threadPoolTaskExecutor.setMaxPoolSize(1); // 最大线程数
        threadPoolTaskExecutor.setQueueCapacity(10000); // 线程池所使用的缓冲队列
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);// 等待任务在关机时完成--表明等待所有线程执行完
        threadPoolTaskExecutor.setAwaitTerminationSeconds(0); // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPoolTaskExecutor.setThreadNamePrefix("AsyncExecutor"); // 线程名称前缀
        // 设置拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {

            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                // TODO Auto-generated method stub
                // System.out.println("-------------》》》线程池超载异常");
                logger.error("线程池超载异常;当前线程数:" + threadPoolExecutor.getActiveCount() + ",未处理任务数:"
                        + threadPoolExecutor.getTaskCount());
            }
        });
        threadPoolTaskExecutor.initialize(); // 初始化
        return threadPoolTaskExecutor;
    }

    // 其它异常处理接口实现
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        // TODO Auto-generated method stub
        AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler = new AsyncUncaughtExceptionHandler() {

            @Override
            public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
                // TODO Auto-generated method stub
                System.out.println("-------------》》》捕获线程异常信息");
                throwable.printStackTrace();
                logger.error("Exception message - " + throwable.getMessage());
                logger.error("Method name - " + method.getName());
                for (Object param : obj) {
                    logger.error("Parameter value - " + param);
                }

            }

        };
        return asyncUncaughtExceptionHandler;
    }

}
