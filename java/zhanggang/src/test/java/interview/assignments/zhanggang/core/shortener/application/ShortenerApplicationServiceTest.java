package interview.assignments.zhanggang.core.shortener.application;

import interview.assignments.zhanggang.config.exception.error.OriginalUrlAlreadyExistException;
import interview.assignments.zhanggang.config.exception.error.ShortenerNotFoundException;
import interview.assignments.zhanggang.core.shortener.adapter.context.impl.ShortIdContextApplicationServiceImpl;
import interview.assignments.zhanggang.core.shortener.adapter.repo.ShortenerRepository;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShortenerApplicationServiceTest {
    @InjectMocks
    private ShortenerApplicationService shortenerApplicationService;
    @Mock
    private ShortenerRepository shortenerRepository;
    @Mock
    private ShortIdContextApplicationServiceImpl shortIdContext;


    @Test
    void test_create_new_shortener_success() {
        final String originalUrl = "https://github.com/scdt-china/interview-assignments";
        when(shortenerRepository.isExist(originalUrl)).thenReturn(Mono.just(false));
        final String shortId = "huAsd1";
        when(shortIdContext.newShortId()).thenReturn(Mono.just(shortId));
        when(shortenerRepository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        final Mono<Shortener> newShortener = shortenerApplicationService.createNewShortener(originalUrl);

        StepVerifier.create(newShortener)
                .assertNext(shortener -> {
                    assertThat(shortener.getId()).isEqualTo(shortId);
                    assertThat(shortener.getOriginalUrl()).isEqualTo(originalUrl);
                    assertThat(shortener.getCreateAt()).isNotNull();
                })
                .verifyComplete();
    }

    @Test
    void test_create_new_shortener_when_url_already_exist() {
        final String originalUrl = "https://github.com/scdt-china/interview-assignments";
        when(shortenerRepository.isExist(originalUrl)).thenReturn(Mono.just(true));

        final Mono<Shortener> newShortener = shortenerApplicationService.createNewShortener(originalUrl);

        StepVerifier.create(newShortener)
                .verifyError(OriginalUrlAlreadyExistException.class);

        verify(shortIdContext, times(0)).newShortId();
        verify(shortenerRepository, times(0)).save(any());
    }

    @Test
    void test_find_by_valid_id() {
        final String id = "test-1";
        final Shortener shortener = mock(Shortener.class);
        when(shortenerRepository.findById(id)).thenReturn(Mono.just(shortener));

        final Mono<Shortener> byId = shortenerApplicationService.findById(id);

        StepVerifier.create(byId)
                .expectNext(shortener)
                .verifyComplete();
    }

    @Test
    void test_find_by_invalid_id() {
        final String id = "test-1";
        when(shortenerRepository.findById(id)).thenReturn(Mono.empty());

        final Mono<Shortener> byId = shortenerApplicationService.findById(id);

        StepVerifier.create(byId)
                .verifyError(ShortenerNotFoundException.class);
    }
}