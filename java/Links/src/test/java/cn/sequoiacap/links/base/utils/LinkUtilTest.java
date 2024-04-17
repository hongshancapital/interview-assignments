package cn.sequoiacap.links.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Liushide
 * @date :2022/4/6 11:18
 * @description : 链接处理工具类测试
 */
@Slf4j
class LinkUtilTest {

    @Test
    @DisplayName("测试 LinkUtil 类的 generateShortCode 方法")
    void generateShortCode() {
        String longUrl = "https://www.abc.com/app/tb-source-app/aiguangjiepc/content/index.html?spm=a21bo.jianhua.201870.4.5af911d9CROzgQ&contentId=5600000340593663082&scm=1007.12846.262044.0&pvid=b611e18f-59ab-4745-9f99-860ded0f7602";
        long startTime = System.currentTimeMillis();
        String shortCode = LinkUtil.generateShortCode(longUrl);
        long endTime = System.currentTimeMillis();
        log.info("shortCode={}, time={} 毫秒", shortCode, endTime - startTime );
        assertEquals(6, shortCode.length());
    }
}