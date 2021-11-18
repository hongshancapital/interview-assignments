package com.youyuzuo.shortdn.service;

import com.youyuzuo.shortdn.dto.DomainNameDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainNameServiceImplTest {

    @Autowired
    private DomainNameService domainNameService;

    @Test
    public void testSaveShortDn(){
        String longDn = "http://"+UUID.randomUUID().toString();
        DomainNameDto dto = domainNameService.save(longDn);
        Assert.notNull(dto, "保存长域名时返回结果不应该为null");
        Assert.hasLength(dto.getShortDN(), "短域名的值不应该为空");
        Assert.isTrue(dto.getShortDN().length()<=8, "短域名的长应该小于8");
    }

    @Test
    public void testRepeatSaveShortDn(){
        String longDn = "http://"+UUID.randomUUID().toString();
        DomainNameDto dto = domainNameService.save(longDn);
        Assert.notNull(dto, "保存长域名时返回结果不应该为null");
        Assert.hasLength(dto.getShortDN(), "短域名的值不应该为空");
        Assert.isTrue(dto.getShortDN().length()<=8, "短域名的长应该小于8");

        DomainNameDto dto2 = domainNameService.save(longDn);
        Assert.notNull(dto2, "保存长域名时返回结果不应该为null");
        Assert.hasLength(dto2.getShortDN(), "短域名的值不应该为空");
        Assert.isTrue(dto.getShortDN().equals(dto2.getShortDN()), "同样的长域名两次应该返回同样的数据");
    }

    @Test
    public void testBatchShortDn(){
        for(int i=0; i<1000;i++){
            testSaveShortDn();
        }
    }

    @Test
    public void testQueryShortDn(){
        String longDn = "http://"+UUID.randomUUID().toString();
        DomainNameDto dto = domainNameService.save(longDn);
        Assert.notNull(dto, "保存长域名时返回结果不应该为null");
        Assert.hasLength(dto.getShortDN(), "短域名的值不应该为空");
        Assert.isTrue(dto.getShortDN().length()<=8, "短域名的长应该小于8");

        DomainNameDto queryResult = domainNameService.queryLongDn(dto.getShortDN());
        Assert.notNull(queryResult, "查询短域名时返回结果不应该为null");
        Assert.hasLength(queryResult.getShortDN(), "短域名的值不应该为空");
        Assert.hasLength(queryResult.getLongDN(), "长域名的值不应该为空");
        Assert.isTrue(longDn.equals(queryResult.getLongDN()), "查询的结果应该与保存时存放的值一致");
    }

    @Test
    public void testQueryExistShortDn(){
        String longDn = "00000000";
        DomainNameDto queryResult = domainNameService.queryLongDn(longDn);
        Assert.isNull(queryResult, "查询不存在的短域名时返回结果应该为null");
    }


}
