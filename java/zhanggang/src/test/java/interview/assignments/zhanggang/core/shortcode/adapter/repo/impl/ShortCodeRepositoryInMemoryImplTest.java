package interview.assignments.zhanggang.core.shortcode.adapter.repo.impl;

import interview.assignments.zhanggang.core.shortcode.model.ShortCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ShortCodeRepositoryInMemoryImplTest {

    @InjectMocks
    public ShortCodeRepositoryInMemoryImpl shortCodeRepository;

    @Test
    void test_generate_new_short_code() {
        final Mono<ShortCode> shortCode = shortCodeRepository.newShortCode();

        StepVerifier.create(shortCode)
                .assertNext(it -> assertThat(it.getSeed()).isEqualTo(1))
                .verifyComplete();
    }

    @Test
    void test_generate_new_short_code_with_concurrence() {
        final List<Mono<ShortCode>> sources = IntStream.range(0, 20)
                .mapToObj(i -> shortCodeRepository.newShortCode())
                .collect(Collectors.toList());

        StepVerifier.create(Flux.concat(sources).collectList())
                .assertNext(shortCodes -> {
                    final Set<Long> seeds = shortCodes.stream().map(ShortCode::getSeed).collect(Collectors.toSet());
                    assertThat(seeds.size()).isEqualTo(20);
                })
                .verifyComplete();
    }

}