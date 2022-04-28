package com.alexyuan.shortlink.common.functions;

import com.alexyuan.shortlink.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description     采用基础自增ID方式的发号器, 优点是结构简单. 如需要扩展多种发号器
 *                  可考虑改变结构使用工厂类, 这里直接构建单一种类发号器实现目标功能
 */
@Slf4j
public class AutoIncreNumSender {

    @Autowired
    private SystemConfig config;

    private static final Logger logger = LoggerFactory.getLogger(AutoIncreNumSender.class);

    // 与SystemConfig中配置对应, 该ID主要代表该发号器自身的ID. 即配置中所指的列.
    private long col_num;

    // 由于在设置中我们保留了六位字符给发号器进行号码生成
    // 即可以生成62 ^ 6 = 56,800,235,584 个号码.
    // 无多行时可生成 62 ^ 7 = 3,521,614,606,208 个号码
    // long最大可表示 9,223,372,036,854,775,807 满足在Long范围内
    // 该数值继承至SystemConfig.
    private long num_max;

    // 该数值代表当前发号器计数位置.
    private long cur_num;

    //TODO：扩展, 可继续细分发号器, 例如每个发号器按指定步长进行发号, 可进一步提升并行数.
    private long step_len;

    public AutoIncreNumSender(long uid) {
        this.col_num = uid;
        this.num_max = config.MAX_CODE_NUM;
    }

    public AutoIncreNumSender(long uid, long num_max) {
        this.col_num = uid;
        this.num_max = num_max;
    }

    // 为UT测试Corner Case准备
    public AutoIncreNumSender(long uid, long num_max, long mock_num) {
        this.col_num = uid;
        this.num_max = num_max;
        this.cur_num = mock_num;
    }

    // 获取当前发号器编号, 发号器编号不允许重新Set, 在初始化时即固定.
    public synchronized Long generateNum() {
        if (cur_num > num_max) {
            logger.error("Generator ID：[" + this.col_num + "], Current Code：[" + this.cur_num + "], Exceed the Limit" +
                    ", End the Service");
            return null;
        }

        long cur_result = cur_num;
        cur_num++;
        return cur_result;
    }

    // 获取当前发号器编号, 发号器编号不允许重新Set, 在初始化时即固定.
    public long getCol_num() {
        return this.col_num;
    }

}
