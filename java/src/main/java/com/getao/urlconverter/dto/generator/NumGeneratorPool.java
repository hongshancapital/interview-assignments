package com.getao.urlconverter.dto.generator;

import com.getao.urlconverter.util.ConverterUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;


@Slf4j
@Data
public class NumGeneratorPool {

    /**
     * 当前策略下Generator池容量固定为62
     */
    // @Value("${app.config.poolSize}")
    public final static int poolSize = 62;

    public final static long generatorMaxSize = (long) Math.pow(62, 7) - 1;

    public final static int headLength = 1;

    public final static int tailLength = 7;

    /**
     * 初始化Generator池
     */
    public NumGeneratorPool() {
        // 当前为固定大小池，所以用数组，也可根据其他策略更改为list或queue
        IdGenerator[] pool = new IdGenerator[poolSize];
        for(int i=0; i<poolSize; i++) {
            IdGenerator generator = new IdGenerator(i, 0, generatorMaxSize);
            pool[i] = generator;
        }
        this.generatorPool = pool;
    }

    /**
     * 发号器池，当前策略下容量固定为62
     */
    public IdGenerator[] generatorPool;

    /**
     * 从Generator池中随机抽取Generator，获取号码并计算对应的URL
     * @return
     */
    public String getEncodedUrl() {
        Random random = new Random();
        int generatorNum = random.nextInt(poolSize);
        long resultNum = generatorPool[generatorNum].getCurNum();
        StringBuilder sb = new StringBuilder();
        sb.append(ConverterUtil.encode(generatorNum, headLength))
            .append(ConverterUtil.encode(resultNum, tailLength));
        return sb.toString();
    }
}
