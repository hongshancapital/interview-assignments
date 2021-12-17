package com.scdt;

import org.junit.Assert;
import org.junit.Test;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Class
 *
 * @Author: lenovo
 * @since: 2021-12-17
 */
public class Swagger2ConfigTest {
    Swagger2Config swagger2Config = new Swagger2Config();

    @Test
    public void testCreateRestApi() throws Exception {
        Docket result = swagger2Config.createRestApi();
        Assert.assertNotNull(result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme