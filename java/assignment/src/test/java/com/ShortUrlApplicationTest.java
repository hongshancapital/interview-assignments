package com;

import com.validator.DomainNameValidatorTest;
import com.service.DomainNameServiceImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DomainNameValidatorTest.class,
        DomainNameServiceImplTest.class
})
public class ShortUrlApplicationTest {


}
