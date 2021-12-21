package com.hongshang.shorturlmodel.impl;

import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * 服务通用返回值类型
 *
 * @author kobe
 * @date 2021/12/19
 */
@RunWith(JMockit.class)
public class SnowFlakeTest {

    @Tested
    SnowFlake snowFlake = new SnowFlake(2,3);

    @Mocked
    System system;

    /**
     * 对产生雪花字符的方法进行测试
     */
    @Test
    public void nextId() {
        long shortUrl= snowFlake.nextId();
        assertNotNull(shortUrl);
    }

    /**
     * 对构造方法进行测试
     */
    @Test
    public void testCreator() {
        try {
            snowFlake = new SnowFlake(-2, 3);
        }catch(IllegalArgumentException e){
            assertEquals("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0",e.getMessage());

        }
    }

    /**
     * 对构造方法进行测试
     */
    @Test
    public void testCreator2() {
        try {
            snowFlake = new SnowFlake(2, -3);
        }catch(IllegalArgumentException e){
            assertEquals("machineId can't be greater than MAX_MACHINE_NUM or less than 0",e.getMessage());

        }
    }

    /**
     * 对系统得到错误时间的测试
     */
    @Test
    public void testNextId2() {

        new Expectations() {
            {
                System.currentTimeMillis();
                result = -2;
            }
            {
                System.currentTimeMillis();
                result = -1;
            }
        };
        try {
            long shortUrl= snowFlake.nextId();
        }catch(RuntimeException e){
            assertEquals("Clock moved backwards.  Refusing to generate id",e.getMessage());
        }
        long lo= snowFlake.nextId();
        assertNotNull(lo);
    }

    /**
     * getNextMill 私有方法进行测试
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void testGetNextMill() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //通过反射调用私有的方法
        Method method = SnowFlake.class.getDeclaredMethod("getNextMill");
        method.setAccessible(true);
        long log = (long) method.invoke(snowFlake);
        assertEquals(0l,log);
    }

}