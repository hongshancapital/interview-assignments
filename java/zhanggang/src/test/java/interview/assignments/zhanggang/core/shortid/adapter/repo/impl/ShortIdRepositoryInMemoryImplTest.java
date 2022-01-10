package interview.assignments.zhanggang.core.shortid.adapter.repo.impl;

import interview.assignments.zhanggang.core.shortid.model.ShortId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ShortIdRepositoryInMemoryImplTest {

    @InjectMocks
    public ShortIdRepositoryInMemoryImpl shortIdRepository;

    @Test
    void test_generate_new_short_code() {
        final Mono<ShortId> shortId = shortIdRepository.newShortId();

        StepVerifier.create(shortId)
                .assertNext(it -> assertThat(it.getId()).isEqualTo(1))
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