package com.domain.urlshortener.core.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 8:27
 */
public class MappingCacheEntryTest {

    @Test
    public void test() {
        MappingCacheEntry mappingCacheEntry = new MappingCacheEntry("a", "b");
        Assertions.assertEquals("a", mappingCacheEntry.getKey());
        Assertions.assertEquals("b", mappingCacheEntry.getValue());
    }

}
