package interview.assignments.zhanggang.core.shortid.adapter.repo.impl;

import interview.assignments.zhanggang.core.shortid.model.ShortId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ShortIdRepositoryInMemoryImplTest {
    private ShortIdRepositoryInMemoryImpl shortIdRepository;

    @BeforeEach
    void setup() {
        shortIdRepository = new ShortIdRepositoryInMemoryImpl();
    }

    @Test
    void test_generate_new_short_code() {
        final Mono<ShortId> shortId = shortIdRepository.newShortId();

        StepVerifier.create(shortId.map(ShortId::getSeed))
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void test_generate_new_short_code_with_concurrence() {
        final Flux<ShortId> shortIds = Flux.concat(IntStream.range(0, 20)
                .mapToObj(i -> shortIdRepository.newShortId())
                .collect(Collectors.toList())
        );

        StepVerifier.create(shortIds)
                .expectNextCount(20)
                .verifyComplete();
    }

}