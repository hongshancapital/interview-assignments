package com.alexyuan.shortlink.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SystemConfig {

    /**
     * 如为在线服务, 可尝试为下列配置定制接口协议进行热更新, 但同时即需要加入合法性检查方法.
     */

    // 短URL长度最长为8, 作为配置预留给后续扩展
    public int MAX_LENGTH = 8;

    // 机器分行占用的位长度, 即8位总长度中使用几位作为平行扩容ID码
    public int ROW_LENGTH = 1;

    // 机器分列占用的位长度, 在本项目中代表单机内的发号器编码占用的位长度
    public int COL_LENGTH = 1;

    // 号码可分配位数, 一般来说等于总长度 - 机器分行占用位数 - 机器分列占用位数
    public int CODE_LENGTH = 1;

    // 进制, 作为配置预留给后续扩展
    public int SCALE = 62;

    // 行数最大值, 指可平行扩容最多多少台机器同时接受服务
    public long MAX_ROW_NUM = 1L;

    // 列数最大值, 指每行内部可分出多少个发号器同时工作
    public long MAX_COL_NUM = 1L;

    // 号码最大值, 指除开行列剩余的代码位可以表达出多少号码量
    public long MAX_CODE_NUM = 1L;

    // 缓存时长
    public long MAX_DURATION = 7200L;

    // 缓存最大条数
    public long MAX_CACHE_NUM = 10000000L;

    /**
     * @Description     初始化方法, 主要作用是将平行扩容的最大行数、列数及能生成的代码数量统计出来
     *                  便于后续系统控制机器数量、发号器数量及单个发号器所支持的最大代码数量。
     */
    @PostConstruct
    public void initialize() {

        if (parameterCheck()) {
            CODE_LENGTH = MAX_LENGTH - ROW_LENGTH - COL_LENGTH;

            for (int pos = 0; pos < ROW_LENGTH; pos++) {
                MAX_ROW_NUM = MAX_ROW_NUM * SCALE;
            }

            for (int pos = 0; pos < COL_LENGTH; pos++) {
                MAX_COL_NUM = MAX_COL_NUM * SCALE;
            }

            for (int pos = 0; pos < CODE_LENGTH; pos++) {
                MAX_CODE_NUM = MAX_CODE_NUM *SCALE;
            }
        }
    }

    /**
     * @Description     TODO:预留给后续可更新配置时进行配置检查的方法
     * @return          本次更新的参数是否合法，若合法 返回True
     */
    private boolean parameterCheck() {

        return true;
    }
}
