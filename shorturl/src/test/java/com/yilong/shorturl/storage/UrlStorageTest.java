package com.yilong.shorturl.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class UrlStorageTest {

    @Test
    public void testSaveAndGet() {
        UrlStorage urlStorageLocal = new UrlStorage(10);
        String longUrl = "https://www.vampire.com/url/looooooong/1";
        String code = urlStorageLocal.save(longUrl);
        Assertions.assertEquals("1Evr03", code);
        Assertions.assertEquals(longUrl, urlStorageLocal.get(code));
    }

    @Test
    public void testStorageFull() {
        UrlStorage urlStorageLocal = new UrlStorage(1);
        String longUrl = "https://www.vampire.com/url/looooooong/1";
        String code = urlStorageLocal.save(longUrl);
        Assertions.assertEquals("1Evr03", code);
        Assertions.assertEquals(longUrl, urlStorageLocal.get(code));

        String longUrl2 = "https://www.vampire.com/url/looooooong/2";
        String code2 = urlStorageLocal.save(longUrl2);
        Assertions.assertEquals(null, code2);
    }

    @Test
    public void testSaveCodeConflict() {
        UrlStorage urlStorageLocal = new UrlStorage(10);
        String longUrl = "https://www.vampire.com/url/looooooong/190";
        String code = urlStorageLocal.save(longUrl);
        Assertions.assertEquals("4OjuKj", code);
        Assertions.assertEquals(longUrl, urlStorageLocal.get(code));


        String conflictLongUrl = "https://www.vampire.com/url/looooooong/69095";
        String otherCode = urlStorageLocal.save(conflictLongUrl);
        Assertions.assertNotEquals("4OjuKj", otherCode);
        Assertions.assertEquals(conflictLongUrl, urlStorageLocal.get(otherCode));
    }

    @Test
    public void testGetNotExistCode() {
        UrlStorage urlStorageLocal = new UrlStorage(10);
        Assertions.assertNull(urlStorageLocal.get("notExistCode"));
    }

    @Test
    public void testConstruct() {
        UrlStorage urlStorageLocal = new UrlStorage(10);
    }

    @Test
    public void testGetShortCode() {
        UrlStorage urlStorageLocal = new UrlStorage(10);
        Class<UrlStorage> clz = UrlStorage.class;
        try {
            Method method = clz.getDeclaredMethod("getShortCode", new Class[] {String.class});
            method.setAccessible(true);

            String obj = (String) method.invoke(urlStorageLocal, "https://www.vampire.com/url/looooooong/190");
            Assertions.assertEquals(null, obj);

            urlStorageLocal.save("https://www.vampire.com/url/looooooong/190");
            obj = (String) method.invoke(urlStorageLocal, "https://www.vampire.com/url/looooooong/190");
            Assertions.assertEquals("4OjuKj", obj);

            urlStorageLocal.save("https://www.vampire.com/url/looooooong/69095");
            obj = (String) method.invoke(urlStorageLocal, "https://www.vampire.com/url/looooooong/69095");
            Assertions.assertNotEquals("4OjuKj", obj);
        } catch (Exception e) {
            Assertions.fail("----------------------------------");
        }
    }
}
