package com.oldnoop.shortlink.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Radix62TransferTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Radix62TransferTest.class);

    @Test
    public void test() {
        {
            for(int i = 1; i <= Math.ceil(Math.log10(Long.MAX_VALUE)); i++){
                long value = 0;
                for(int j = 0; j < i; j++){
                    value += (long)Math.pow(10, j);
                }
                String valueStr = Radix62Transfer.to(value);
                long strValue = Radix62Transfer.from(valueStr);
                LOGGER.info(String.format("value:%s,str:%s", value, valueStr));
                Assert.assertEquals(value, strValue);
            }

            for(int i = 1; i < Math.ceil(Math.log10(Long.MAX_VALUE)); i++){
                long value = 0;
                for(int j = 0; j < i; j++){
                    value += (long)Math.pow(10, j) * 9;
                }
                String valueStr = Radix62Transfer.to(value);
                long strValue = Radix62Transfer.from(valueStr);
                LOGGER.info(String.format("value:%s,str:%s", value, valueStr));
                Assert.assertEquals(value, strValue);
            }

            for(int i = 1; i < Math.ceil(Math.log10(Long.MAX_VALUE)); i++){
                long value = (long)Math.pow(10, i);
                String valueStr = Radix62Transfer.to(value);
                long strValue = Radix62Transfer.from(valueStr);
                LOGGER.info(String.format("value:%s,str:%s", value, valueStr));
                Assert.assertEquals(value, strValue);
            }

            {
                long value = Long.MAX_VALUE;
                String valueStr = Radix62Transfer.to(value);
                long strValue = Radix62Transfer.from(valueStr);
                LOGGER.info(String.format("value:%s,str:%s", value, valueStr));
                Assert.assertEquals(value, strValue);
            }

        }
    }
}