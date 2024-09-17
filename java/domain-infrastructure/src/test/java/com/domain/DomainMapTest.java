package com.domain;

import com.domain.cache.DomainMap;
import org.junit.Test;

/**
 * @author: xielongfei
 * @date: 2022/01/09
 * @description:
 */
public class DomainMapTest {

    @Test
    public void testMap() {
        DomainMap.getShortKeyMap().put("key", "value");
        DomainMap.getLongKeyMap().put("key", "value");
    }
}
