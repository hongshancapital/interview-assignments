package architecture;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class ApplicationServiceTest extends ArchitectureTestBase {
    @Test
    void application_service_should_named_end_with_application_service() {
        classes().that().resideInAnyPackage("..application..")
                .should().haveSimpleNameEndingWith("ApplicationService")
                .check(coreClasses);
    }

    @Test
    void application_service_should_control_by_framework() {
        classes().that().resideInAnyPackage("..application..")
                .should().beAnnotatedWith(Service.class)
                .check(coreClasses);
    }
}
