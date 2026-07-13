package com.liupf.tiny.url;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import com.liupf.tiny.url.utils.IdCreator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class IdCreatorTest {

    /**
     * 64进制下8位长度的最大值
     */
    private static final long MAX_ID_LINK8 = Double.valueOf(Math.pow(64, 8)).longValue();


    @Test
    @DisplayName("构造ID生成器")
    public void testBuildIdCreator() {
        // 正常创建IDC
        new IdCreator(0);
        new IdCreator(1);
        // 机器编号超过最大值
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new IdCreator(2));
        assertEquals("worker Id can't be greater than 1 or less than 0", exception.getMessage());
        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> new IdCreator(-1));
        assertEquals("worker Id can't be greater than 1 or less than 0", exception1.getMessage());
    }

    @Test
    @DisplayName("生成ID-注入业务属性")
    public void testBizId() {
        IdCreator idc = new IdCreator(0);
        long id = idc.nextId(1);
        Assert.isTrue(id < MAX_ID_LINK8, "生成有效ID");
        // 机器编号超过最大值
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> idc.nextId(2));
        assertEquals("data center Id can't be greater than 1 or less than 0", exception.getMessage());
        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> idc.nextId(-1));
        assertEquals("data center Id can't be greater than 1 or less than 0", exception1.getMessage());
    }

    @Test
    @DisplayName("生成ID-批量")
    public void testBatchNextId() {
        IdCreator idc = new IdCreator(0);

        StopWatch stopWatch = new StopWatch("批量生成ID");
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            Assert.isTrue(idc.nextId() < MAX_ID_LINK8, "生成有效ID");
        }
        stopWatch.stop();
        // 本地测试：耗时为3906 ms，QPS为：25W
        log.info("批量生成ID耗时：{} ms", stopWatch.getTotalTimeMillis());
    }

}
