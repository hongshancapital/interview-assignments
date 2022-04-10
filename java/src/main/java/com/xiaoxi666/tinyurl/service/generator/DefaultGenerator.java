package com.xiaoxi666.tinyurl.service.generator;

import com.google.common.collect.Lists;
import com.xiaoxi666.tinyurl.config.TinyUrlConfig;
import com.xiaoxi666.tinyurl.service.codec.Base62Codec;
import com.xiaoxi666.tinyurl.service.codec.HashCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/12
 * @Version: 1.0
 * @Description: 默认发号器实现
 */
@Component
@ConditionalOnProperty(name = "generator.impl", havingValue = "default")
public class DefaultGenerator implements Generator, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGenerator.class);

    @Autowired
    private TinyUrlConfig config;

    private List<Wrapper> wrappers = Collections.synchronizedList(Lists.newArrayList());

    /**
     * 发号
     *
     * 若发号器发出的号码经过编码后超过了所占位数，则将当前发号器作废，继续使用其他发号器。
     * 若所有发号器都被作废，则无法继续产生有效号码，返回""。
     *
     * @return
     */
    @Override
    public String generate() {
        String generatorCode = "";
        String id = "";

        Wrapper wrapper;
        do {
            wrapper = select();
            if (wrapper != null) {
                generatorCode = wrapper.generatorCode;
                id = HashCodec.encode(wrapper.atomicLong.incrementAndGet());
            }
        } while (id.length() > config.getIdBit() && wrappers.remove(wrapper));

        if (generatorCode.isEmpty() || id.isEmpty()) {
            LOGGER.error("无法继续发号，所有发号器都用尽了！");
            return "";
        } else {
            return generatorCode + id;
        }
    }

    /**
     * 选择其中一个发号器
     *
     * @return wrapper 如果发号器List为空，则返回null
     */
    private Wrapper select() {
        if (wrappers.isEmpty()) {
            return null;
        }
        int indexOfGenerator = ThreadLocalRandom.current().nextInt(wrappers.size());
        return wrappers.get(indexOfGenerator);
    }

    /**
     * 初始化发号器
     */
    @Override
    public void afterPropertiesSet() {
        for (int i = 0; i < config.getGeneratorCnt(); ++i) {
            // 发号器编号从1开始，所以加1
            wrappers.add(new Wrapper(Base62Codec.encode(i + 1), new AtomicLong()));
        }
    }

    /**
     * 发号器包装类
     */
    public static class Wrapper {
        /**
         * 发号器编号
         */
        String generatorCode;
        /**
         * 发号器使用AtomicLong实现
         */
        AtomicLong atomicLong;

        public Wrapper(String generatorCode, AtomicLong atomicLong) {
            this.generatorCode = generatorCode;
            this.atomicLong = atomicLong;
        }
    }
}
