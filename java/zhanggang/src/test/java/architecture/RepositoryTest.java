package architecture;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class RepositoryTest extends ArchitectureTestBase {
    @Test
    void repository_should_named_end_with_repository() {
        classes().that().resideInAnyPackage("..repository")
                .should().haveSimpleNameEndingWith("repository")
                .check(coreClasses);
    }

    @Test
    void repository_implement_should_control_by_framework_and_named_end_with_impl() {
        classes().that().resideInAnyPackage("..repository.impl")
                .should().beAnnotatedWith(Repository.class)
                .andShould().haveSimpleNameEndingWith("Impl")
                .check(coreClasses);
    }
}
