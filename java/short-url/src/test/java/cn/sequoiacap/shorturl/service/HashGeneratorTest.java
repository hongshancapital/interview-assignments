package cn.sequoiacap.shorturl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HashGeneratorTest {
    @Autowired
    private HashGenerator generator;

    @Test
    public void testGenerate() {
        String id = generator.generate("https://www.baidu.com", 0);
        Assertions.assertEquals("8jzx4E", id);

        id = generator.generate("https://www.baidu.com", 1);
        Assertions.assertEquals("ktG3gD", id);
    }
}
