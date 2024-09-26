package com.alibaba;

import com.alibaba.service.IDomainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author caozx
 * @desc
 * @date 2022年04月19日 15:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private IDomainService domainService;

    @Test
    public void test1() {
        String url = "https://doctor.houaijk.com/app/ddas?a=1";
        String shortUrl = domainService.getShortDomain(url);
        System.out.println(shortUrl);
        String longUrl = domainService.getLongDomain(shortUrl);
        System.out.println(longUrl);
    }

}
