//package com.oldnoop.shortlink.util;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
//import static org.junit.Assert.*;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({SnowFlakeGenerator.class})
//public class SnowFlakeGeneratorTest {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SnowFlakeGeneratorTest.class);
//
//    @Test
//    public void test() {
//        SnowFlakeGenerator snowFlakeGenerator = new SnowFlakeGenerator(1,1, 1);
//        long id1 = snowFlakeGenerator.nextId();
//        long id2 = snowFlakeGenerator.nextId();
//
//        LOGGER.info(String.format("length:%s, id1:%s, id2:%s, id1 < id2 :%s", String.valueOf(id1).length(),  id1, id2, (id1 < id2)));
//        Assert.assertTrue(id2 > id1);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testSequenceEx1(){
//        new SnowFlakeGenerator(33,1, 0);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testSequenceEx2(){
//        new SnowFlakeGenerator(-1,1, 0);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testSequenceEx3(){
//        new SnowFlakeGenerator(1,33, 0);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testSequenceEx4(){
//        new SnowFlakeGenerator(1,-1, 0);
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void testSequenceEx5() throws Exception {
//        SnowFlakeGenerator generator = new SnowFlakeGenerator(1, 1, 0);
//        long time = System.currentTimeMillis() + 100;
//        Field lastTimestamp = SnowFlakeGenerator.class.getDeclaredField("lastTimestamp");
//        lastTimestamp.setAccessible(true);
//        lastTimestamp.set(generator, time);
//        generator.nextId();
//    }
//
//    @Test
//    public void testGenerateSameId(){
//        long time = System.currentTimeMillis();
//        PowerMockito.mockStatic(System.class);
//        PowerMockito.when(System.currentTimeMillis()).thenReturn(time);
//        SnowFlakeGenerator snowFlakeGenerator = new SnowFlakeGenerator(1,1, 1);
//        long id1 = snowFlakeGenerator.nextId();
//        long id2 = snowFlakeGenerator.nextId();
//        Assert.assertTrue(id1 < id2);
//    }
//
//    @Test
//    public void testTilNextMillis() throws Exception {
//        long time = System.currentTimeMillis() + 100;
//        SnowFlakeGenerator snowFlakeGenerator = new SnowFlakeGenerator(1,1, 1);
//        Method method = SnowFlakeGenerator.class.getDeclaredMethod("tilNextMillis", long.class);
//        method.setAccessible(true);
//        long newTime = (long) method.invoke(snowFlakeGenerator, time);
//        Assert.assertTrue(newTime > time);
//    }
//
//}