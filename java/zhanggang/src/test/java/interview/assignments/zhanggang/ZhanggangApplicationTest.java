package interview.assignments.zhanggang;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class ZhanggangApplicationTest {

    @Test
    void test_run_application_on_main() {
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            final String[] args = new String[]{};
            springApplication.when(() -> SpringApplication.run(ZhanggangApplication.class, args))
                    .thenReturn(mock(ConfigurableApplicationContext.class));

            assertDoesNotThrow(() -> ZhanggangApplication.main(args));
            springApplication.verify(() -> SpringApplication.run(ZhanggangApplication.class, args));
        }
    }
}