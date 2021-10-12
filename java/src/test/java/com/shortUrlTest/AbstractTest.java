package com.shortUrlTest;

import org.junit.Before;
import static org.mockito.MockitoAnnotations.initMocks;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author jeffrey
 * @Date 2021/10/12
 * @description:
 */
@RunWith(SpringRunner.class)
public abstract class AbstractTest {

    @Before
    public void setUpRoot() {
        initMocks(this);
        this.setUp();
    }


    public void setUp() {

    }
}
