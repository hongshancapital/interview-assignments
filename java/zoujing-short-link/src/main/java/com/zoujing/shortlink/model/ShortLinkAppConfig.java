package com.zoujing.shortlink.model;

import com.zoujing.shortlink.exception.ShortLinkException;
import com.zoujing.shortlink.exception.ShortLinkResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class ShortLinkAppConfig {
    /**
     * 短连接字符集长度
     */
    @Value("${app.config.characterSet}")
    public String characterSet;

    /**
     * 短链接总长度
     */
    @Value("${app.config.shortLinkLength}")
    public Long shortLinkLength;

    /**
     * 应用占位长度大小
     */
    @Value("${app.config.sourceAppBitSize}")
    public Long sourceAppBitSize;

    /**
     * 应用机器占位长度大小
     */
    @Value("${app.config.machineBitSize}")
    public Long machineBitSize;

    /**
     * 发号器编号占位长度大小
     */
    @Value("${app.config.IDGeneratorNumBitSize}")
    public Long IDGeneratorNumBitSize;

    /**
     * 单发号器号数最大值（报警阈值）
     */
    @Value("${app.config.IDMaxNum}")
    public Long IDMaxNum;

    /**
     * 缓存过期时长(单位：秒)
     */
    @Value("${app.config.expire}")
    public Long expire;

    /**
     * 缓存最大长度
     */
    @Value("${app.config.cacheMaxSize}")
    public Long cacheMaxSize;

    /**
     * 测试本地机器号
     */
    @Value("${app.config.testMachineId}")
    public Long testMachineId;

    /**
     * 单服务器发号器最大个数
     */
    public Long IDGeneratorMaxSize = 1L;

    /**
     * 发号器id编号占位长度
     */
    public Long IDBitSize = 1L;


    @PostConstruct
    public void init() {

        // 检查配置参数
        checkConfig();

        // 从配置中计算发号器个数
        for (int i = 0; i < IDGeneratorNumBitSize; i++) {
            IDGeneratorMaxSize *= characterSet.length();
        }

        IDBitSize = shortLinkLength - sourceAppBitSize - machineBitSize - IDGeneratorNumBitSize;
        Long maxSize = 1L;
        // 从配置中计算发号器最大值
        for (int i = 0; i < IDBitSize; i++) {
            maxSize *= characterSet.length();
        }

        // 如果配置计算比设定的阈值更小 则替换配置中的最大值
        if (IDMaxNum > maxSize) {
            IDMaxNum = maxSize;
        }
        log.info("config start success.IDMaxNum={},IDGeneratorMaxSize={}", IDMaxNum, IDGeneratorMaxSize);
    }

    private void checkConfig() {
        if (shortLinkLength <= 0 || sourceAppBitSize < 0 || machineBitSize <= 0 || IDGeneratorNumBitSize <= 0) {
            throw new ShortLinkException(ShortLinkResultEnum.CONFIG_ERROR);
        }
        if (shortLinkLength - sourceAppBitSize - machineBitSize - IDGeneratorNumBitSize <= 0) {
            throw new ShortLinkException(ShortLinkResultEnum.CONFIG_ERROR);
        }
    }
}
