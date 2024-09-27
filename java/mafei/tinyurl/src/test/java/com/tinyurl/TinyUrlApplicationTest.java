package com.tinyurl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TinyUrlApplication.class})
public class TinyUrlApplicationTest {

    @Test
    public void contextLoads() {
        System.out.println("Tiny Url Servcie Start");
    }
}
