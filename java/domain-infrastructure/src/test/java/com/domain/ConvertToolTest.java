package com.domain;

import com.domain.common.ConvertTool;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author: xielongfei
 * @date: 2022/01/09
 * @description:
 */
public class ConvertToolTest {

    @Test
    public void testAddDomain() {
        ConvertTool tool = new ConvertTool();
        String text = tool.toString(-100);
        Assert.assertNotNull(text);

        String text1 = tool.toString("https://www.baidu.com");
        Assert.assertNotNull(text1);
    }
}
