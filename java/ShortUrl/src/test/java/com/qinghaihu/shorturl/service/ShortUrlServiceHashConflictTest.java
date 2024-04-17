package com.qinghaihu.shorturl.service;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(PowerMockRunner.class)
@SpringBootTest
public class ShortUrlServiceHashConflictTest {

    ShortUrlService shortUrlService = new ShortUrlService();



    /**
     * 测试Hash冲突
     */
    @Test()
    public void testHashConflict() throws Exception {

        HashCode code1 = HashCode.fromInt(74774744);
        HashCode code2 = HashCode.fromInt(74774744);
        HashCode code3 = HashCode.fromInt(99999999);
        HashFunction hashFunction = PowerMockito.mock(HashFunction.class);
        Whitebox.setInternalState(shortUrlService, "hashFunction",hashFunction); //属性替换为mock对象
        PowerMockito.when(hashFunction.hashString(Mockito.any(CharSequence.class), eq(StandardCharsets.UTF_8))).thenReturn(code1,code2,code3);
        String firstResult = shortUrlService.transferToShortUrl("first");
        String secodResult = shortUrlService.transferToShortUrl("second");
        assertNotEquals(firstResult,secodResult); //hash冲突，两次的转换结果应不一样
    }



}
