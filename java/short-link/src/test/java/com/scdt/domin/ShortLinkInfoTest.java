package com.scdt.domin;

import com.scdt.domin.po.ShortLinkInfo;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

/**
 * ShortLinkInfoTest
 *
 * @author weixiao
 * @date 2022-04-26 18:54
 */
public class ShortLinkInfoTest {

    @Test
    public void testShortLinkInfo() {
        assertPojoMethodsForAll(ShortLinkInfo.class).quickly()
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR);
    }
}
