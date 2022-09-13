package io.nigelwy.javaassignments.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.nigelwy.javaassignments.Constants.ORIGIN_URL;
import static io.nigelwy.javaassignments.Constants.SHORT_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InMemoryUrlRepositoryTest {

    private InMemoryUrlRepository repo;

    @BeforeEach
    void setup() {
        repo = new InMemoryUrlRepository();
    }

    @Nested
    class Save {

        @Test
        void should_save_success() {
            assertDoesNotThrow(() -> repo.save(SHORT_URL, ORIGIN_URL));
        }
    }

    @Nested
    class Get {

        @Test
        void should_return_null_when_key_not_exists() {
            assertThat(repo.get(SHORT_URL)).isNull();
        }

        @Test
        void should_return_result_when_key_exists() {
            repo.save(SHORT_URL, ORIGIN_URL);

            assertThat(repo.get(SHORT_URL)).isEqualTo(ORIGIN_URL);
        }
    }
}