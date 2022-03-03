package com.hongshan.homework;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class URLConvertTest {

    @Test
    public void short2longTestCase(){
        log.info(Long.parseLong("F09A18D3",16)+"");
        log.info("short2longTestCase");
    }
}
