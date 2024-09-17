package architecture;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class ControllerTest extends ArchitectureTestBase {
    @Test
    void controller_should_named_end_with_controller() {
        classes().that().resideInAnyPackage("..api")
                .should().haveSimpleNameEndingWith("Controller")
                .check(coreClasses);
    }

    @Test
    void controller_should_control_by_framework() {
        classes().that().resideInAnyPackage("..api")
                .should().beAnnotatedWith(RestController.class)
                .check(coreClasses);
    }
}
