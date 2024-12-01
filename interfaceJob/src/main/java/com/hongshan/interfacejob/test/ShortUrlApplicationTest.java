package com.hongshan.interfacejob.test;

import com.hongshan.interfacejob.service.DomainNameServiceImplTest;
import com.hongshan.interfacejob.validator.DomainNameValidatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DomainNameValidatorTest.class,
        DomainNameServiceImplTest.class
})
public class ShortUrlApplicationTest {

}
