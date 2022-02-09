package com.scdt.china.shorturl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   ShortUrlCacheEvictTest.class,
        ShortUrlControllerTest.class,
        ShortUrlGenerateConflictTest.class,
        ShortUrlServiceTest.class
})
public class ShortUrlTest {
}
