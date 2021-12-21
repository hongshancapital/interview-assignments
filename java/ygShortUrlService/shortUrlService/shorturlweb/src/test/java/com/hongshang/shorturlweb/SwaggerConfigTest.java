package com.hongshang.shorturlweb;

import mockit.Tested;
import org.junit.Test;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.Assert.*;

/**
 * swagger配置设置类测试
 *
 * @author kobe
 * @date 2021/12/19
 */
public class SwaggerConfigTest {

    @Tested
    private SwaggerConfig swaggerConfig;

    /**
     * 测试swagger配置类是否成功完成
     */
    @Test
    public void testTransformToShortUrl() {
        Docket docket = swaggerConfig.createRestApi();
        assertNotNull(docket);
    }
}