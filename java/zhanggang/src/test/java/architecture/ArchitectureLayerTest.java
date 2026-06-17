package architecture;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitectureLayerTest extends ArchitectureTestBase {
    private static final String LAYER_APPLICATION = "application";
    private static final String LAYER_API = "api";
    private static final String LAYER_CONTEXT = "context";
    private static final String LAYER_REPOSITORY = "repository";

    @Test
    void application_service_should_only_by_accessed_by_api_and_context() {

        layeredArchitecture()
                .layer(LAYER_APPLICATION).definedBy("..application..")
                .layer(LAYER_API).definedBy("..adapter.api..")
                .layer(LAYER_CONTEXT).definedBy("..adapter.context..")
                .layer(LAYER_REPOSITORY).definedBy("..adapter.repo..")

                .whereLayer(LAYER_APPLICATION).mayOnlyBeAccessedByLayers(LAYER_API, LAYER_CONTEXT)
                .whereLayer(LAYER_API).mayNotBeAccessedByAnyLayer()
                .whereLayer(LAYER_CONTEXT).mayOnlyBeAccessedByLayers(LAYER_APPLICATION)
                .whereLayer(LAYER_REPOSITORY).mayOnlyBeAccessedByLayers(LAYER_APPLICATION)
                .check(coreClasses);
    }
}
