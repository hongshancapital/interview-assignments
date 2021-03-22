package xiejin.java.interview;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xiejin.java.interview.controller.DomainController;
import xiejin.java.interview.service.DomainService;
@WebAppConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class InterviewJavaRanguoApplicationTests {

    @Before
    public void  init() {
       log.info("======开始测试==========");
    }

    @After
    public void end() {
        log.info("========测试结束========");
    }
}
