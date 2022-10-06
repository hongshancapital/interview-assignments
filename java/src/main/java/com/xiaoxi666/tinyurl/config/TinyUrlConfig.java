package com.xiaoxi666.tinyurl.config;

import com.google.common.annotations.VisibleForTesting;
import com.xiaoxi666.tinyurl.service.codec.Base62Codec;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/12
 * @Version: 1.0
 * @Description: 服务配置
 */
@Configuration
@Setter // for testing
@Getter
public class TinyUrlConfig implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(TinyUrlConfig.class);

    // base62进制编码
    private static final int RADIX = 62;

    // 短链接服务配置文件（目前用于存储机器编号）
    private static final String CONFIG_FILE_PATH = "/usr/local/etc/tinyurl/tinyurl.config";
    // 配置项名称和值的分隔符
    private static final String SEPARATOR = "=";
    // 配置项名称
    private static final String MACHINE_NO = "machineNo";

    /**
     * 短链接总位数
     */
    @Value("${bit.total}")
    private int totalBit;

    /**
     * 机器编号位数
     */
    @Value("${bit.machine}")
    private int machineBit;

    /**
     * 发号器位数
     */
    @Value("${bit.generator}")
    private int generatorBit;

    /**
     * 号码位数
     */
    private int idBit;

    /**
     * 机器编号
     */
    private String machineCode;

    /**
     * 单机发号器个数
     */
    private int generatorCnt;


    @Override
    public void afterPropertiesSet() throws Exception {
        checkBits();
        Config config = parseConfigFile();
        machineCode = Base62Codec.encode(config.machineNo);
        generatorCnt = calculatorGeneratorCnt(generatorBit);
    }

    /**
     * 计算单机发号器个数。
     *
     * @param generatorBit 发号器占用位数
     * @return 单机发号器个数
     */
    private int calculatorGeneratorCnt(int generatorBit) {
        return (int) Math.pow(RADIX, generatorBit);
    }

    /**
     * 解析配置文件
     *
     * @return 全部配置项
     */
    private Config parseConfigFile() {
        Config config = new Config();
        try (Stream<String> stream = Files.lines(Paths.get(CONFIG_FILE_PATH))) {
            List<String> lines = stream.filter(Objects::nonNull).collect(Collectors.toList());
            for (String line : lines) {
                if (line.startsWith(MACHINE_NO)) {
                    config.setMachineNo(parseMachineNo(line));
                }
                // 以后若有其他配置可以追加
            }
        } catch (IOException e) {
            String errorMsg = String.format("配置文件读取错误！%s", e.getMessage());
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        return config;
    }

    @Setter
    public static class Config {
        /**
         * 机器编号
         */
        int machineNo;

        // 以后若有其他配置可以追加
    }

    /**
     * 解析机器编号
     *
     * @param line
     */
    @VisibleForTesting
    protected int parseMachineNo(String line) {
        String[] kv = line.split(SEPARATOR);
        if (kv.length == 2) {
            try {
                int machineNo = Integer.parseInt(kv[1]);
                if (Math.pow(RADIX, machineBit) < machineNo || machineNo < 0) {
                    String errorMsg = String.format("机器编号超出限制! " +
                            "机器编号占用位数：%d, 机器编号：%d", machineBit, machineNo);
                    LOGGER.error(errorMsg);
                    throw new IllegalArgumentException(errorMsg);
                } else {
                    return machineNo;
                }
            } catch (NumberFormatException e) {
                String errorMsg = String.format("配置文件读取错误，无法解析机器编号（必须为正整数）：%s", kv[1]);
                LOGGER.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
        } else {
            String errorMsg = String.format("配置文件无效，无法解析机器编号，配置项：%s", line);
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * 检查配置信息的有效性
     */
    @VisibleForTesting
    protected void checkBits() {
        if (totalBit <= 0 || machineBit <= 0 || generatorBit <= 0) {
            String errorMsg = String.format("占用位数必须为正整数! " +
                    "短链接总位数：%d, 机器编号占用位数：%d, 发号器占用位数：%d", totalBit, machineBit, generatorBit);
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        if (generatorBit > 2) {
            String errorMsg = String.format("发号器占用位数不应超过2! 发号器占用位数：%d", generatorBit);
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        if (totalBit - machineBit - generatorBit <= 0) {
            String errorMsg = String.format("机器编号和发号器占用位数已经超过短链接总位数，无法发号! " +
                    "短链接总位数：%d, 机器编号占用位数：%d, 发号器占用位数：%d", totalBit, machineBit, generatorBit);
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        idBit = totalBit - machineBit - generatorBit;
    }

}
