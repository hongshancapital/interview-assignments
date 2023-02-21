package cn.sequoiacap.shorturl.service;

import cn.sequoiacap.shorturl.exception.StoreException;
import cn.sequoiacap.shorturl.store.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ShortUrlServiceImplTest {
    @Value("${short.url.prefix}")
    private String prefix;
    @MockBean
    private Store store;
    @MockBean
    private ShortUrlGenerator generator;

    @Autowired
    private ShortUrlServiceImpl impl;

    private static final String ORIGINAL_URL = "https://www.baidu.com";
    private static final String SHORT_URL_ID0 = "abc";
    private static final String SHORT_URL_ID1 = "def";

    @BeforeEach
    public void init() {
        Mockito.when(generator.generate(ORIGINAL_URL, 0)).thenReturn(SHORT_URL_ID0);
        Mockito.when(generator.generate(ORIGINAL_URL, 1)).thenReturn(SHORT_URL_ID1);
    }

    @Test
    public void testGenerate() throws StoreException {
        // 重复的 originalUrl
        Mockito.when(store.get(SHORT_URL_ID0)).thenReturn(ORIGINAL_URL);
        String result = impl.generate(ORIGINAL_URL);
        Assertions.assertEquals(prefix + SHORT_URL_ID0, result);

        // 存储不存在, 第一次写入, 且写入成功
        Mockito.when(store.get(SHORT_URL_ID0)).thenReturn(null);
        Mockito.when(store.write(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        result = impl.generate(ORIGINAL_URL);
        Assertions.assertEquals(prefix + SHORT_URL_ID0, result);

        // 存储不存在, 写入的时候冲突, 继续尝试生成, 写入成功
        Mockito.when(store.write(SHORT_URL_ID0, ORIGINAL_URL)).thenReturn(false);
        Mockito.when(store.write(SHORT_URL_ID1, ORIGINAL_URL)).thenReturn(true);
        result = impl.generate(ORIGINAL_URL);
        Assertions.assertEquals(prefix + SHORT_URL_ID1, result);

        // 一直重复
        Mockito.when(store.get(SHORT_URL_ID0)).thenReturn("xyz");
        Mockito.when(store.write(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        result = impl.generate(ORIGINAL_URL);
        Assertions.assertNull(result);
    }
}
