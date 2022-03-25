package cn.sequoiacap.shorturl.store;

import cn.sequoiacap.shorturl.exception.StoreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@SpringBootTest
public class GuavaStoreTest {
    @SpyBean
    private GuavaStore store;

    @Test
    public void testWrite() throws StoreException, InterruptedException {
        Lock lock = Mockito.mock(Lock.class);
        Mockito.when(store.getLock(Mockito.anyString())).thenReturn(lock);
        String shortUrlId = "abc";
        String originalUrl = "https://www.baidu.com";

        // 获取锁失败
        Mockito.when(lock.tryLock(100, TimeUnit.MILLISECONDS)).thenReturn(false);
        StoreException exception = Assertions.assertThrows(StoreException.class,
                () -> store.write(shortUrlId, originalUrl));
        Assertions.assertEquals("lock " + shortUrlId + "'s store failed", exception.getMessage());

        // 获取成功, 写成功
        Mockito.when(lock.tryLock(100, TimeUnit.MILLISECONDS)).thenReturn(true);
        boolean result = store.write(shortUrlId, originalUrl);
        Assertions.assertTrue(result);

        // 当前记录已写入
        Mockito.when(store.get(Mockito.anyString())).thenReturn(originalUrl);
        result = store.write(shortUrlId, originalUrl);
        Assertions.assertTrue(result);

        // 记录冲突
        Mockito.when(store.get(Mockito.anyString())).thenReturn("https://www.google.com");
        result = store.write(shortUrlId, originalUrl);
        Assertions.assertFalse(result);
    }
}
