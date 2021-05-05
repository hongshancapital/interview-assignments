package com.wangxinyu.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootApplication
public class InterviewApplication {
    public static final AtomicBoolean acceptNewRequest = new AtomicBoolean(true);

    public static void main(String[] args) {
        SpringApplication.run(InterviewApplication.class, args);
    }

    static {
        new Thread(() -> {
            while (true) {
                //空闲内存
                int free = (int) Runtime.getRuntime().freeMemory() / 1024 / 1024;
                //总内存
                int total = (int) Runtime.getRuntime().totalMemory() / 1024 / 1024;
                //使用占百分比
                int memUsedpercentage = (total - free) * 100 / total;
                String status = memUsedpercentage + "%";
                if (memUsedpercentage > 90) {
                    acceptNewRequest.set(false);
                }else{
                    acceptNewRequest.set(true);
                }
                System.out.println("剩余内存:" + free + "MB");//剩余内存:127014KB
                System.out.println("已经使用内存：" + (total - free) + "MB");
                System.out.println("总内存:" + total + "MB");//总内存:129024KB
                System.out.println("使用占百分比:" + status);//使用占百分比:1%
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
