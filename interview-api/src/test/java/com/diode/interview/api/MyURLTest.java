package com.diode.interview.api;

import com.diode.interview.domain.entity.MyURL;
import com.diode.interview.domain.exception.BizException;
import com.diode.interview.infrastructure.ability.transformer.MD5Transformer;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author unlikeha@163.com
 * @date 2022/4/29
 */
@Slf4j
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MyURLTest {

    @Mock
    private MD5Transformer transformer;

    @InjectMocks
    private MyURL myURL;


    @Test
    public void testLongToShort(){

        myURL.setUrl(null);
        String s = myURL.longToShort(transformer);
        Assert.assertTrue(Strings.isNullOrEmpty(s));

        myURL.setUrl("aaaaaa");
        Assertions.assertThrows(BizException.class, () -> myURL.longToShort(transformer));

        String s2 = myURL.longToShort(null);
        Assert.assertTrue(Strings.isNullOrEmpty(s2));
    }
}
