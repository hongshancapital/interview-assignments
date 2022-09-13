package io.nigelwy.javaassignments.service;

import io.nigelwy.javaassignments.repository.UrlRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.nigelwy.javaassignments.Constants.ORIGIN_URL;
import static io.nigelwy.javaassignments.Constants.SHORT_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceTest {

    @Mock
    private UrlGenerator generator;

    @Mock
    private UrlRepository repository;

    @InjectMocks
    private ShortUrlService service;

    @Nested
    class GenerateShortUrl {

        @Test
        void should_save_mapping_and_return_short_url() {
            when(generator.generateShortUrl(any()))
                    .thenReturn(SHORT_URL);
            var shortUrl = service.generateShortUrl(ORIGIN_URL);

            verify(generator).generateShortUrl(ORIGIN_URL);
            verify(repository).save(SHORT_URL, ORIGIN_URL);
            assertThat(shortUrl).isEqualTo(SHORT_URL);
        }

    }

    @Nested
    class GetOriginUrl {

        @Test
        void should_return_origin_url() {
            when(repository.get(any())).thenReturn(ORIGIN_URL);

            var originUrl = service.getOriginUrl(SHORT_URL);

            verify(repository).get(SHORT_URL);
            assertThat(originUrl).isEqualTo(ORIGIN_URL);
        }

    }

}