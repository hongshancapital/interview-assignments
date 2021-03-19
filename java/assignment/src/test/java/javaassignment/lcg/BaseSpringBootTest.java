package javaassignment.lcg;

import com.sun.corba.se.spi.logging.LogWrapperFactory;
import javaassignment.lcg.controller.v1.UrlController;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseSpringBootTest {

    private static final Logger log = (Logger) LoggerFactory.getLogger(BaseSpringBootTest.class);

    @Before
    public void init() {
        log.info("开始测试...");
    }

    @After
    public void after() {
        log.info("测试结束...");
    }
}