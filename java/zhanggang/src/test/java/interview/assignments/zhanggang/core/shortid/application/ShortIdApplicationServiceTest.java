package interview.assignments.zhanggang.core.shortid.application;

import interview.assignments.zhanggang.config.exception.error.ShortIdMaximumLimitException;
import interview.assignments.zhanggang.config.properties.ShortenerProperties;
import interview.assignments.zhanggang.core.shortid.adapter.repo.ShortIdRepository;
import interview.assignments.zhanggang.core.shortid.model.ShortId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortIdApplicationServiceTest {
    @InjectMocks
    private ShortIdApplicationService shortIdApplicationService;
    @Mock
    private ShortIdRepository shortIdRepository;
    @Mock
    private ShortenerProperties shortenerProperties;


    @Test
    void test_new_short_id_success() {
        when(shortIdRepository.newShortId()).thenReturn(Mono.just(new ShortId(100L)));
        when(shortenerProperties.getMaxLength()).thenReturn(10);

        final Mono<ShortId> shortIdMono = shortIdApplicationService.newShortId();

        StepVerifier.create(shortIdMono.map(ShortId::getSeed))
                .expectNext(100L)
                .verifyComplete();
    }

    @Test
    void test_new_short_id_reached_max_length_limit_error() {
        when(shortIdRepository.newShortId()).thenReturn(Mono.just(new ShortId(1000000L)));
        when(shortenerProperties.getMaxLength()).thenReturn(1);

        final Mono<ShortId> shortIdMono = shortIdApplicationService.newShortId();

        StepVerifier.create(shortIdMono.map(ShortId::getSeed))
                .expectError(ShortIdMaximumLimitException.class)
                .verify();
    }
}