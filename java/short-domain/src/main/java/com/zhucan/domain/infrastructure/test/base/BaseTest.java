package com.zhucan.domain.infrastructure.test.base;

import com.zhucan.domain.App;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 18:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment =SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public abstract class BaseTest {
}
