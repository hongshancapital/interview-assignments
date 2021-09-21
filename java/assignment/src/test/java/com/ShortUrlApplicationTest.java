package com;

import com.service.MatchStorageServiceImplTest;
import com.validator.UrlTransformValidatorTest;
import com.service.UrlTransformServiceImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UrlTransformValidatorTest.class,
        UrlTransformServiceImplTest.class,
        MatchStorageServiceImplTest.class
})
public class ShortUrlApplicationTest {

}
