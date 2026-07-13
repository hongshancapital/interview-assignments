package com.coderdream.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileOperateHelperTest {
    @Resource
    private FileOperateHelper fleOperateHelper;

    @Test
    public void testReadFile() {
        Assert.assertEquals("", fleOperateHelper.readFile("12"));
        Assert.assertEquals("6", fleOperateHelper.readFile("machineId"));
    }

    @Test
    public void testWriteFile() {
        fleOperateHelper.writeFile("test.txt", "test");
        Assert.assertEquals("test", fleOperateHelper.readFile("info/test.txt"));
    }
}
