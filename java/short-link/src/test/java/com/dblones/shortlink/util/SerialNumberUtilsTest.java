package com.dblones.shortlink.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SerialNumberUtilsTest {

    @Autowired
    private SerialNumberUtils serialNumberUtils;

    @Test
    public void getSerialNumber(){
        Long serialNumber = serialNumberUtils.getSerialNumber();
        System.out.println(serialNumber);
    }

    @Test
    public void resetAndGetSerialNumber(){
        Long serialNumber = serialNumberUtils.resetAndgetSerialNumber();
        System.out.println(serialNumber);
    }
}
