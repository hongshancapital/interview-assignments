package com.shortUrlTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @Author jeffrey
 * @Date 2021/10/12
 * @description:
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DomainNameValidatorTest.class,
        DomainNameServiceImplTest.class
})
public class ShortUrlApplicationTest {
}
