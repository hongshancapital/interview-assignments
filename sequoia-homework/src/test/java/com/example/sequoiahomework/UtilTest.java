package com.example.sequoiahomework;

import com.example.sequoiahomework.common.utils.JvmUtils;
import com.example.sequoiahomework.common.utils.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Irvin
 * @description 工具类测试
 * @date 2021/10/9 20:55
 */
@RunWith(SpringRunner.class)
@Slf4j
public class UtilTest {

    @Test
    public void shortUrlTest() {
        String originalUrl = "https://github.com/scdt-china/interview-assignments";
        log.info("原本的链接为：{}", originalUrl);
        log.info("转换后链接为：{}",  UrlUtils.longToShort(originalUrl));
    }

    @Test
    public void testJvm(){
        long usableByte = JvmUtils.getUsableByte();
        log.info("可用内存大小为:{}", usableByte);
    }

    @Test
    public void testIsUrl(){
        String originalUrl = "https://github.com/scdt-china/interview-assignments";
        log.info("测试{}是否是url结果:{}", originalUrl, UrlUtils.isUrl(originalUrl));
    }
}
