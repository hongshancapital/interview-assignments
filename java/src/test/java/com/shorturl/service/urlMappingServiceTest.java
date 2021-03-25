package com.shorturl.service;

import com.shorturl.utils.NumberUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * Created by ruohanpan on 21/3/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class urlMappingServiceTest {

    @Autowired
    private urlMappingService service;

    private List<String> urls;

    @Before
    public void prepareData() {

    }

    @Test
    public void compress() throws Exception {

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    service.urlCompress("http://www.baidu.com/" + UUID.randomUUID());
                } catch (Exception e) {
                    ExceptionUtils.getStackTrace(e);
                }
            }).start();
        }

        service.urlCompress("http://www.baidu.com/");
        service.urlCompress("http://www.baidu.com/");
    }

    @Test
    public void decompress() throws Exception {
        Long code = 1001L;
        String base62 = NumberUtils.decimal2base62(code);
        String url = service.urlDecompress(base62);
        System.out.println(url);
    }
}