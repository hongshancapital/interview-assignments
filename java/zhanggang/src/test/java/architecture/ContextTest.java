package architecture;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class ContextTest extends ArchitectureTestBase {
    @Test
    void context_should_named_end_with_context() {
        classes().that().resideInAnyPackage("..context")
                .should().haveSimpleNameEndingWith("Context")
                .check(coreClasses);
    }

    @Test
    void context_implement_should_control_by_framework_and_named_end_with_impl() {
        classes().that().resideInAnyPackage("..context.impl")
                .should().beAnnotatedWith(Component.class)
                .andShould().haveSimpleNameEndingWith("Impl")
                .check(coreClasses);
    }
}
