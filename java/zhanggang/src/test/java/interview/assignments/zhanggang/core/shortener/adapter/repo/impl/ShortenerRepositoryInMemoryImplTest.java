package interview.assignments.zhanggang.core.shortener.adapter.repo.impl;

import interview.assignments.zhanggang.core.shortener.model.Shortener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

class ShortenerRepositoryInMemoryImplTest {
    private ShortenerRepositoryInMemoryImpl shortenerRepositoryInMemory;

    @BeforeEach
    void setup() {
        shortenerRepositoryInMemory = new ShortenerRepositoryInMemoryImpl();
    }

    @Test
    void test_save_shortener() {
        final Shortener shortener = new Shortener(1, "https://shortener-zg.com", Instant.now());
        final Mono<Shortener> save = shortenerRepositoryInMemory.save(shortener);

        StepVerifier.create(save)
                .expectNext(shortener)
                .verifyComplete();
    }

    @Test
    void test_find_shortener_by_a_valid_id() {
        final Shortener shortener = new Shortener(1, "https://shortener-zg.com", Instant.now());
        shortenerRepositoryInMemory.save(shortener).block();

        final Mono<Shortener> byId = shortenerRepositoryInMemory.findById(1);
        StepVerifier.create(byId)
                .expectNext(shortener)
                .verifyComplete();
    }

    @Test
    void test_find_shortener_by_a_invalid_id() {
        final Mono<Shortener> byId = shortenerRepositoryInMemory.findById(99);
        StepVerifier.create(byId)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void test_an_exist_shortener() {
        final Shortener shortener = new Shortener(1, "https://shortener-zg.com", Instant.now());
        shortenerRepositoryInMemory.save(shortener).block();

        final Mono<Shortener> isExist = shortenerRepositoryInMemory.isExist("https://shortener-zg.com");
        StepVerifier.create(isExist)
                .expectNext(shortener)
                .verifyComplete();
    }

    @Test
    void test_an_not_exist_shortener() {
        final Mono<Shortener> isExist = shortenerRepositoryInMemory.isExist("https://test-zg.com");
        StepVerifier.create(isExist)
                .expectNextCount(0)
                .verifyComplete();
    }
}