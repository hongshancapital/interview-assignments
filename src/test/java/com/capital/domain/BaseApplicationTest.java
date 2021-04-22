package com.capital.domain;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ConversionApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseApplicationTest {

    /**
     * 校验service方法是否正常运行
     */
    protected <T extends Throwable> void assertThrown(Runnable block, Class<T> clazz) {
        boolean throwed = false;
        try {
            block.run();
        } catch (Throwable e) {
            throwed = true;
            Assert.assertEquals(clazz, e.getClass());
        }
        Assert.assertTrue("未能抛出异常", throwed);
    }

}
