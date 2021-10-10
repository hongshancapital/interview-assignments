package com.example.sequoiahomework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Irvin
 * @description 容器对象
 * @date 2021/10/9 20:22
 */
@Component
@Slf4j
public class Container {

    /**
     * 长链接为key，短链接为value的集合
     */
    public ConcurrentHashMap<String, String> longKey;

    /**
     * 短链接为key，长链接为value的集合
     */
    public ConcurrentHashMap<String, String> shortKey;

    /**
     * 项目启动的时候初始化容器
     * @author Irvin
     * @date 2021/10/9
     */
    @PostConstruct
    public void init() {
        longKey = new ConcurrentHashMap<>(1024);
        shortKey = new ConcurrentHashMap<>(1024);
        log.info("容器初始化完成");
    }

}
