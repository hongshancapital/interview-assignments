package com.zoujing.shortlink.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleIDGeneratorTest {
    @Test
    public void testSimpleIDGenerator() {
        SimpleIDGenerator simpleIDGenerator = new SimpleIDGenerator(3l,100l,1000l);
        assertNotNull(simpleIDGenerator.getGeneratorNo());
        assertNotNull(simpleIDGenerator.getNextId());

        SimpleIDGenerator simpleIDGenerator2 = new SimpleIDGenerator(3l,100l,80l);
        assertNull(simpleIDGenerator2.getNextId());
    }
}
