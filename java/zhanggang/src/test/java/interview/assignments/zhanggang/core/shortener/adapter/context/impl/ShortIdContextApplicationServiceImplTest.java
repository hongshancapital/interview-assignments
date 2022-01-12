package interview.assignments.zhanggang.core.shortener.adapter.context.impl;

import interview.assignments.zhanggang.core.shortid.application.ShortIdApplicationService;
import interview.assignments.zhanggang.core.shortid.model.ShortId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortIdContextApplicationServiceImplTest {
    @InjectMocks
    private ShortIdContextApplicationServiceImpl shortIdContext;
    @Mock
    private ShortIdApplicationService shortIdApplicationService;


    @Test
    void test_new_short_Id() {
        ShortId shortId = mock(ShortId.class);
        when(shortId.getValue()).thenReturn("test-id");
        when(shortIdApplicationService.newShortId()).thenReturn(Mono.just(shortId));

        final Mono<String> newShortId = shortIdContext.newShortId();
        StepVerifier.create(newShortId)
                .expectNext("test-id")
                .verifyComplete();
    }
}