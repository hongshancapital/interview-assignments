package com.lisi.urlconverter.service;

import com.lisi.urlconverter.enumeration.UCErrorType;
import com.lisi.urlconverter.model.UCException;
import com.lisi.urlconverter.starter.Starter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * @description: 测试ConverterService
 * @author: li si
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Starter.class)
public class ConverterServiceImplTest {
    @Autowired
    private ConverterService converterService;

    @Test
    public void testConvertAndGet() {
        String longUrl = "verylongurl";
        String shortUrl = converterService.convert(longUrl);
        Assert.assertNotNull(shortUrl);

        String getResult = converterService.getLongUrl(shortUrl);
        Assert.assertEquals(longUrl, getResult);
    }

    @Test
    public void testGetNull() {
        String errCode = null;
        try{
            converterService.getLongUrl("notexist");
        }catch (UCException e){
            errCode = e.getUcErrorType().getErrCode();
        }
        Assert.assertEquals(errCode, UCErrorType.URL_NOT_FOUND_EXCEPTION.getErrCode());
    }

    /**
     * 测试内存清理线程是否工作正常
     * @throws Exception
     */
    @Test
    public void testCleanThread() throws Exception{
        String longUrl = "verylong";
        String shortUrl = converterService.convert(longUrl);
        Assert.assertNotNull(converterService.getLongUrl(shortUrl));
        Thread.sleep(7000L);
        String errCode = "";
        try {
            converterService.getLongUrl(shortUrl);
        }catch (UCException e){
            errCode = e.getUcErrorType().getErrCode();
        }
        Assert.assertEquals(errCode, UCErrorType.URL_NOT_FOUND_EXCEPTION.getErrCode());
    }

    /**
     * 测试内存检查线程工作是否正常
     * @throws Exception
     */
    @Test
    public void testMemoryCheck() throws Exception {
        String errCode = "";
        try {
            for(int i = 0; i < 5; i++){
                converterService.convert(i + "");
            }
            Thread.sleep(2000);
            converterService.convert("verylong");
        }catch (UCException e){
            errCode = e.getUcErrorType().getErrCode();
        }
        Assert.assertEquals(UCErrorType.MEMORY_NOT_ENOUGH_EXCEPTION.getErrCode(), errCode);
        Thread.sleep(5000);
        Assert.assertNotNull(converterService.convert("verylong"));
    }

}
