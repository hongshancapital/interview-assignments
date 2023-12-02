package interview.assignments.zhanggang.core.shortener.application;

import interview.assignments.zhanggang.config.exception.error.ShortenerNotFoundException;
import interview.assignments.zhanggang.config.properties.ShortenerProperties;
import interview.assignments.zhanggang.core.shortener.adapter.context.impl.ShortIdContextApplicationServiceImpl;
import interview.assignments.zhanggang.core.shortener.adapter.repo.ShortenerRepository;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShortenerApplicationServiceTest {
    @InjectMocks
    private ShortenerApplicationService shortenerApplicationService;
    @Mock
    private ShortenerRepository shortenerRepository;
    @Mock
    private ShortIdContextApplicationServiceImpl shortIdContext;
    @Mock
    private ShortenerProperties shortenerProperties;

    @Test
    void test_create_new_shortener() {
        final String originalUrl = "https://github.com/scdt-china/interview-assignments";
        when(shortenerRepository.isExist(originalUrl)).thenReturn(Mono.empty());
        when(shortenerRepository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        when(shortIdContext.newShortId()).thenReturn(Mono.just("s1"));
        when(shortenerProperties.getShortUrlHost()).thenReturn("https://shortener.com");

        final Mono<String> shortUrl = shortenerApplicationService.getShortUrl(originalUrl);

        StepVerifier.create(shortUrl)
                .expectNext("https://shortener.com/s1")
                .verifyComplete();
    }

    @Test
    void test_create_shortener_when_url_already_exist() {
        final String originalUrl = "https://github.com/scdt-china/interview-assignments";
        final Shortener shortener = mock(Shortener.class);
        when(shortener.getShortUrl("https://shortener.com")).thenReturn("https://shortener.com/s1");
        when(shortenerRepository.isExist(originalUrl)).thenReturn(Mono.just(shortener));
        when(shortenerProperties.getShortUrlHost()).thenReturn("https://shortener.com");

        final Mono<String> shortUrl = shortenerApplicationService.getShortUrl(originalUrl);

        StepVerifier.create(shortUrl)
                .expectNext("https://shortener.com/s1")
                .verifyComplete();

        verify(shortIdContext, times(0)).newShortId();
        verify(shortenerRepository, times(0)).save(any());
    }

    @Test
    void test_get_original_by_valid_short_url() {
        final String id = "s1";
        final Shortener shortener = mock(Shortener.class);
        when(shortener.getOriginalUrl()).thenReturn("testOriginalUrl");
        when(shortenerRepository.findById(id)).thenReturn(Mono.just(shortener));

        final Mono<String> originalUrl = shortenerApplicationService.getOriginalUrl("https://shortener.com/s1");

        StepVerifier.create(originalUrl)
                .expectNext("testOriginalUrl")
                .verifyComplete();
    }

    @Test
    void test_get_original_by_invalid_short_url() {
        try (MockedStatic<Shortener> shortener = mockStatic(Shortener.class)) {
            final String id = "s1";
            final String shortUrl = "shortUrl";
            when(shortenerRepository.findById(id)).thenReturn(Mono.empty());
            shortener.when(() -> Shortener.parseId(shortUrl)).thenReturn(id);

            final Mono<String> originalUrl = shortenerApplicationService.getOriginalUrl(shortUrl);

            StepVerifier.create(originalUrl)
                    .verifyError(ShortenerNotFoundException.class);
        }
    }
}