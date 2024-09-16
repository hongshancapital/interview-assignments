package com.xiaoxi666.tinyurl.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/21
 * @Version: 1.0
 * @Description: 配置文件解析
 */
class TinyUrlConfigTest {

    TinyUrlConfig tinyUrlConfig;

    @BeforeEach
    void init() {
        tinyUrlConfig = new TinyUrlConfig();
        tinyUrlConfig.setTotalBit(8);
        tinyUrlConfig.setMachineBit(1);
        tinyUrlConfig.setGeneratorBit(1);
    }

    @Nested
    @DisplayName("解析机器编号")
    class ParseMachineNo{

        @Test
        @DisplayName("正常解析")
        void parse_normal() {
            int machineNo = tinyUrlConfig.parseMachineNo("machineNo=2");
            Assertions.assertEquals(2, machineNo);
        }

        @Test
        @DisplayName("下限溢出")
        void parse_overflowLow() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                tinyUrlConfig.parseMachineNo("machineNo=-2");
            }, "机器编号超出限制! 机器编号占用位数：1, 机器编号：-2");
        }

        @Test
        @DisplayName("上限溢出")
        void parse_overflowHigh() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                tinyUrlConfig.parseMachineNo("machineNo=63");
            }, "机器编号超出限制! 机器编号占用位数：1, 机器编号：63");
        }

        @Test
        @DisplayName("机器编号必须为正整数")
        void parse_mustInteger() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                tinyUrlConfig.parseMachineNo("machineNo=abc");
            }, "配置文件读取错误，无法解析机器编号（必须为正整数）：abc");
        }

        @Test
        @DisplayName("配置项格式不正确")
        void parse_nonPair() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                tinyUrlConfig.parseMachineNo("abcdefg");
            }, "配置文件无效，无法解析机器编号，配置项：abcdefg");
        }
    }


    @Nested
    @DisplayName("校验位数占用")
    class CheckBits {

        @Test
        @DisplayName("校验正常")
        void check_normal() {
            tinyUrlConfig.setTotalBit(8);
            tinyUrlConfig.setMachineBit(1);
            tinyUrlConfig.setGeneratorBit(2);
            tinyUrlConfig.checkBits();
            Assertions.assertEquals(5, tinyUrlConfig.getIdBit());
        }

        @Test
        @DisplayName("下限溢出")
        void check_overflowLow() {
            tinyUrlConfig.setTotalBit(8);
            tinyUrlConfig.setMachineBit(-1);
            tinyUrlConfig.setGeneratorBit(1);
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                tinyUrlConfig.checkBits();
            }, "占用位数必须为正整数! 短链接总位数：8, 机器编号占用位数：-1, 发号器占用位数：1");
        }

        @Test
        @DisplayName("发号器占用位数太大")
        void check_tooBigGeneratorBit() {
            tinyUrlConfig.setTotalBit(8);
            tinyUrlConfig.setMachineBit(1);
            tinyUrlConfig.setGeneratorBit(3);
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                tinyUrlConfig.checkBits();
            }, "发号器占用位数不应超过2! 发号器占用位数：3");
        }

        @Test
        @DisplayName("机器编号和发号器占用位数太大，已经没有号码的位置了，无法发号")
        void check_couldNotGenerateCode() {
            tinyUrlConfig.setTotalBit(8);
            tinyUrlConfig.setMachineBit(6);
            tinyUrlConfig.setGeneratorBit(2);
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                tinyUrlConfig.checkBits();
            }, "机器编号和发号器占用位数已经超过短链接总位数，无法发号! 短链接总位数：8, 机器编号占用位数：6, 发号器占用位数：2");
        }
    }


}