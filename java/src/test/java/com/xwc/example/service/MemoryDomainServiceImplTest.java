package com.xwc.example.service;

import com.xwc.example.ShortDomainApplication;
import com.xwc.example.service.impl.MemoryDomainServiceImpl;
import com.xwc.example.utils.TestDataUtils;
import org.junit.After;
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
public class MemoryDomainServiceImplTest {

    @Value("${domain.maxCapacity:1000}")
    private Long maxCapacity;

    @Autowired
    private DomainService memoryDomainServiceImpl;

    @Autowired

    /**
     * 测试是否能正常获取数据
     */
    @Test
    public void getShortDomain() {
        String shortDomain = memoryDomainServiceImpl.getShortDomain(TestDataUtils.genderLongDomain());
        Assert.assertNotNull(shortDomain);
    }

    /**
     * 测试同一个地址两次能否获取到同一个段地址
     */
    @Test
    public void getSingletonShortDomain() {
        String address = TestDataUtils.genderLongDomain();
        String shortDomain = memoryDomainServiceImpl.getShortDomain(address);
        String shortDomain1 = memoryDomainServiceImpl.getShortDomain(address);
        Assert.assertEquals(shortDomain1, shortDomain);
    }

    /**
     * 测试系统容量
     */
    @Test
    public void getSingletonShortDomainMaxCapacity() {
        for (int i = 0; i < maxCapacity + 10; i++) {
            String shortDomain = memoryDomainServiceImpl.getShortDomain(TestDataUtils.genderLongDomain());
            if (shortDomain == null) {
                memoryDomainServiceImpl.clear();
                return;
            }
        }
        Assert.fail();
    }


    /**
     * 两个地址获取不通的短地址
     */
    @Test
    public void getMultiShortDomain() {
        String shortDomain = memoryDomainServiceImpl.getShortDomain(TestDataUtils.genderLongDomain());
        String shortDomain1 = memoryDomainServiceImpl.getShortDomain(TestDataUtils.genderLongDomain());
        Assert.assertNotEquals(shortDomain1, shortDomain);
    }

    /**
     * 查询一个存在的任务
     */
    @Test
    public void findLengthDomain() {
        String longDomain = TestDataUtils.genderLongDomain();
        String shortDomain = memoryDomainServiceImpl.getShortDomain(longDomain);
        String lengthDomain = memoryDomainServiceImpl.findLengthDomain(shortDomain);
        Assert.assertNotNull(lengthDomain);
        lengthDomain = memoryDomainServiceImpl.findLengthDomain(shortDomain);
        Assert.assertNotNull(lengthDomain);
        memoryDomainServiceImpl.getShortDomain(longDomain);
        String lengthDomain2 = memoryDomainServiceImpl.findLengthDomain(shortDomain);
        Assert.assertEquals(lengthDomain2, lengthDomain);
    }

    /**
     * 查询一个不存在的任务
     */
    @Test
    public void lengthDomainNoneExist() {
        String lengthDomain = memoryDomainServiceImpl.findLengthDomain(TestDataUtils.genderLongDomain());
        Assert.assertNull(lengthDomain);
    }


}
