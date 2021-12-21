package com.hongshang;

import com.hongshang.shorturlmodel.api.IUrlDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * springboot启动类
 *
 * @author kobe
 * @date 2021/12/19
 */
@SpringBootApplication
@EnableWebMvc
public class ShorturlwebApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ShorturlwebApplication.class, args);
        startSchdueRemoveDelayDataTask(run);
    }

    /**
     * 启动定时清理 过期 长地址的任务 ，设置为每天凌晨2点执行
     *
     * @param run
     */
    private static void startSchdueRemoveDelayDataTask(ConfigurableApplicationContext run){
        Runnable runable = new Runnable(){
            @Override
            public void run() {
                IUrlDao urlDao = run.getBean(IUrlDao.class);
                urlDao.removeDelayData();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int initialDelay = 24-hour+2; // 凌晨2点开始任务
        service.scheduleAtFixedRate(runable,initialDelay,24, TimeUnit.HOURS);
    }



}
