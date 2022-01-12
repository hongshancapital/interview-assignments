package interview.assignments.zhanggang;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class ZhanggangApplicationTest {

    @Test
    void test_run_application_on_main() {
        try (MockedStatic<SpringApplication> springApplication = Mockito.mockStatic(SpringApplication.class)) {
            final String[] args = new String[]{};
            springApplication.when(() -> SpringApplication.run(ZhanggangApplication.class, args))
                    .thenReturn(mock(ConfigurableApplicationContext.class));

            assertDoesNotThrow(() -> ZhanggangApplication.main(args));
        }
    }
}