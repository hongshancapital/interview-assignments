package com.scc.base;

import com.scc.base.entity.UrlDO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author renyunyi
 * @date 2022/4/26 2:14 PM
 * @description EntityTests
 **/
public class EntityTests {

    @Test
    public void test(){
        UrlDO urlDO = new UrlDO();
        urlDO.setShortUrl("abc");
        assert StringUtils.equals("abc", urlDO.getShortUrl());
    }

}
