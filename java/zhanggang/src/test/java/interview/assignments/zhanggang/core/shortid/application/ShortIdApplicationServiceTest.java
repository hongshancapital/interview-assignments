package interview.assignments.zhanggang.core.shortid.application;

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

    @Test
    void newShortId() {
        when(shortIdRepository.newShortId()).thenReturn(Mono.just(new ShortId(100L)));

        final Mono<ShortId> shortIdMono = shortIdApplicationService.newShortId();

        StepVerifier.create(shortIdMono.map(ShortId::getSeed))
                .expectNext(100L)
                .verifyComplete();
    }
}