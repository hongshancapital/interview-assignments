package com.domain.url.helper;

import com.domain.url.exception.IExceptionCode;
import com.domain.url.exception.ServiceException;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssertHelperTest {

    @Test
    public void notBlank() {
        Assertions.assertDoesNotThrow(() -> AssertHelper.notBlank("test", new IExceptionCode() {
            @Override
            public String name() {
                return "fail";
            }

            @Override
            public String getMessage() {
                return "错误";
            }
        }));

        Assertions.assertThrows(ServiceException.class, () -> AssertHelper.notBlank(null, new IExceptionCode() {
            @Override
            public String name() {
                return "fail";
            }

            @Override
            public String getMessage() {
                return "错误";
            }
        }));

        Assertions.assertThrows(ServiceException.class, () -> AssertHelper.notBlank("", new IExceptionCode() {
            @Override
            public String name() {
                return "fail";
            }

            @Override
            public String getMessage() {
                return "错误";
            }
        }));
    }

    @Test
    public void notNull() {
        Assertions.assertDoesNotThrow(() -> AssertHelper.notNull("test", new IExceptionCode() {
            @Override
            public String name() {
                return "fail";
            }

            @Override
            public String getMessage() {
                return "错误";
            }
        }));

        Assertions.assertThrows(ServiceException.class, () -> AssertHelper.notNull(null, new IExceptionCode() {
            @Override
            public String name() {
                return "fail";
            }

            @Override
            public String getMessage() {
                return "错误";
            }
        }));
    }

    @Test
    public void isTrue() {
        Assertions.assertDoesNotThrow(() -> AssertHelper.isTrue(true, new IExceptionCode() {
            @Override
            public String name() {
                return "fail";
            }

            @Override
            public String getMessage() {
                return "错误";
            }
        }));

        Assertions.assertThrows(ServiceException.class, () -> AssertHelper.isTrue(false, new IExceptionCode() {
            @Override
            public String name() {
                return "fail";
            }

            @Override
            public String getMessage() {
                return "错误";
            }
        }));
    }

    @Test
    public void notEmpty() {
        List<Integer> list = Lists.newArrayList(1, 2, 3);

        Assertions.assertDoesNotThrow(() -> AssertHelper.notEmpty(list, new IExceptionCode() {
            @Override
            public String name() {
                return "fail";
            }

            @Override
            public String getMessage() {
                return "错误";
            }
        }));

        Assertions.assertThrows(ServiceException.class, () -> AssertHelper.notEmpty(null, new IExceptionCode() {
            @Override
            public String name() {
                return "fail";
            }

            @Override
            public String getMessage() {
                return "错误";
            }
        }));

        Assertions.assertThrows(ServiceException.class, () -> AssertHelper.notEmpty(Lists.newArrayList(), new IExceptionCode() {
            @Override
            public String name() {
                return "fail";
            }

            @Override
            public String getMessage() {
                return "错误";
            }
        }));
    }
}