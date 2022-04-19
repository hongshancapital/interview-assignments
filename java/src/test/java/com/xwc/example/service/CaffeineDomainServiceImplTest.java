package com.xwc.example.service;

import com.xwc.example.ShortDomainApplication;
import com.xwc.example.utils.TestDataUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/4/13 19:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortDomainApplication.class)
public class CaffeineDomainServiceImplTest {

    @Autowired
    private DomainService caffeineDomainServiceImpl;


    /**
     * 测试是否能正常获取数据
     */
    @Test
    public void getShortDomain() {
        String address = TestDataUtils.genderLongDomain();
        String shortDomain = caffeineDomainServiceImpl.getShortDomain(address);
        String shortDomain1 = caffeineDomainServiceImpl.getShortDomain(address);
        Assert.assertEquals(shortDomain1, shortDomain);
    }



    /**
     * 两个地址获取不通的短地址
     */
    @Test
    public void getMultiShortDomain() {
        String shortDomain = caffeineDomainServiceImpl.getShortDomain(TestDataUtils.genderLongDomain());
        String shortDomain1 = caffeineDomainServiceImpl.getShortDomain(TestDataUtils.genderLongDomain());
        Assert.assertNotEquals(shortDomain1, shortDomain);
    }

    /**
     * 查询一个存在的任务
     */
    @Test
    public void findLengthDomain() {
        String shortDomain = caffeineDomainServiceImpl.getShortDomain(TestDataUtils.genderLongDomain());
        String lengthDomain = caffeineDomainServiceImpl.findLengthDomain(shortDomain);
        Assert.assertNotNull(lengthDomain);
        lengthDomain = caffeineDomainServiceImpl.findLengthDomain(shortDomain);
        Assert.assertNotNull(lengthDomain);
    }

    /**
     * 查询一个不存在的任务
     */
    @Test
    public void lengthDomainNoneExist() {
        String lengthDomain = caffeineDomainServiceImpl.findLengthDomain(TestDataUtils.genderLongDomain());
        Assert.assertNull(lengthDomain);
    }

    // 查询一个不符合最低要求的路径
    @Test
    public void lengthDomainFailedData() {
        String lengthDomain = caffeineDomainServiceImpl.findLengthDomain("123123");
        Assert.assertNull(lengthDomain);
    }


}
