package com.zjm.sdct_work;

import com.zjm.sdct_work.service.ShortcutService;
import com.zjm.sdct_work.util.ShortcutUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author:   billzzzhang
 * Date:     2022/3/20 下午10:26
 * Desc:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class DuplicateShortcutTest {

    @MockBean
    private ShortcutUtil util;


    @Autowired
    @InjectMocks
    private ShortcutService service;


    private int num = 0;
    private String Str1 = "abcdefgh";
    private String Str2 = "abcd4568";

    @Test
    public void TestDuplicate() {


        Mockito.when(util.generatorRandomStr()).thenReturn(Str1).thenReturn(Str1).thenReturn(Str1).thenReturn(Str2);

        String goodUrl = "http://www.baidu.com";
        String shortcut = util.generatorRandomStr();
        String result = service.storeUrlAndShortcut(goodUrl, shortcut);
        Assert.assertEquals(result, Str1);

        String goodUrl2 = "http://www.baidu.com";
        String shortcut2 = util.generatorRandomStr();
        String reusult2 = service.storeUrlAndShortcut(goodUrl2, shortcut2);
        Assert.assertEquals(reusult2, result);

        String goodUrl3 = "http://www.sina.com";
        String shortcut3 = util.generatorRandomStr();
        String reuslt3 = service.storeUrlAndShortcut(goodUrl3, shortcut3);
        Assert.assertNotEquals(reuslt3, reusult2);
    }

}
