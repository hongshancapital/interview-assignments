package com.rad.shortdomainname.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IdWorkerTest {

    @Test
    void testConstructor(){
        try {
            new IdWorker(32, 1, 1);
        }catch (Exception e){
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            new IdWorker(-1, 1, 1);
        }catch (Exception e){
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            new IdWorker(31, 32, 1);
        }catch (Exception e){
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            new IdWorker(31, -1, 1);
        }catch (Exception e){
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testNextId() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        IdWorker idWorker = new IdWorker(1,1,1);
        Field lastTimestampField = IdWorker.class.getDeclaredField("lastTimestamp");
        lastTimestampField.setAccessible(true);
        long lastTimestamp = System.currentTimeMillis()+1000*60*5;
        lastTimestampField.set(idWorker, lastTimestamp);
        try {
            idWorker.nextId();
        }catch (Exception e){
            assertTrue(e.getMessage().contains("Clock moved backwards."));
        }

        Method  tilNextMillisMethod = IdWorker.class.getDeclaredMethod("tilNextMillis",long.class);
        lastTimestamp = System.currentTimeMillis()+1000*3;
        tilNextMillisMethod.setAccessible(true);
        tilNextMillisMethod.invoke(idWorker, lastTimestamp);
    }
}