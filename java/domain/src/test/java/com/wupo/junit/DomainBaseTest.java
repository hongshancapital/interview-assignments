package com.wupo.junit;

import com.wupo.exam.DomainNameApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = DomainNameApplication.class)
@RunWith(SpringRunner.class)
public class DomainBaseTest {

    @Test
    public void test(){

    }
}
