package com.skyscreen.urldemo.cache;


import com.skyscreen.urldemo.utils.PatternUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MapCacheTest {
    @Test
    public void init(){
        MapCache.init();
        Assertions.assertTrue(Boolean.TRUE);
    }

    @Test
    public void getLongKey(){
        MapCache.map.put("abcdefgh","http://www.baidu.com");
        Assertions.assertEquals("abcdefgh",MapCache.getLongKey("http://www.baidu.com"));
    }
}
