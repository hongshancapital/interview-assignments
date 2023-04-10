package com.example.baiyang.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/18
 * @description: 打包集合单元测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ShortUrlGenerateUtilTest.class,
        ShortUrlUtilTest.class,
        ShortUrlControllerTest.class,
        ShortUrlServiceImplTest.class
})
public class SuitTest {
}
