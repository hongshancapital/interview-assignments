package com.david.urlconverter.service.impl;

import com.david.urlconverter.service.dubbo.IUrlConverterIDRangeSOAService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UrlConverterIDRangeSOAServiceImplTest {

    @Autowired
    private IUrlConverterIDRangeSOAService urlConverterIDRangeSOAService;

    @Test
    public void getNextAvailableRange() {
        for(int i=0;i<20;i++){
            final int j = i;
            /**
             * add sleep test to see the order
             */
            /*try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            new Thread(() -> {
                System.out.println("Thread " + j + " get range:" + urlConverterIDRangeSOAService.getNextAvailableRange());
            }).start();
        }
    }
}