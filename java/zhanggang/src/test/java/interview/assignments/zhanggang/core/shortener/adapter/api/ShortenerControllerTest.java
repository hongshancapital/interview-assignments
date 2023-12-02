package interview.assignments.zhanggang.core.shortener.adapter.api;

import interview.assignments.zhanggang.config.api.ApiErrorHandler;
import interview.assignments.zhanggang.config.exception.SystemException;
import interview.assignments.zhanggang.config.exception.error.LockTimeoutException;
import interview.assignments.zhanggang.config.exception.error.ShortIdMaximumLimitException;
import interview.assignments.zhanggang.config.exception.error.ShortenerNotFoundException;
import interview.assignments.zhanggang.config.exception.error.URLFormatException;
import interview.assignments.zhanggang.core.shortener.adapter.api.request.URLRequest;
import interview.assignments.zhanggang.core.shortener.application.ShortenerApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ShortenerController.class)
@ImportAutoConfiguration(ApiErrorHandler.class)
class ShortenerControllerTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private ShortenerApplicationService shortenerApplicationService;

    @Test
    void test_get_short_url_by_original_url_happy_path() {
        final String originalUrl = "https://github.com/scdt-china/interview-assignments";
        final String shortUrl = "https://shortener.com/s1";
        when(shortenerApplicationService.getShortUrl(originalUrl)).thenReturn(Mono.just(shortUrl));
        final URLRequest urlRequest = new URLRequest();
        urlRequest.setUrl(originalUrl);

        webClient.post()
                .uri("/shortener/short-url")
                .bodyValue(urlRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.url").isEqualTo(shortUrl);
    }

    @Test
    void test_get_original_url_by_short_url_happy_path() {
        final String shortUrl = "https://shortener.com/s1";
        final String originalUrl = "https://github.com/scdt-china/interview-assignments";
        when(shortenerApplicationService.getOriginalUrl(shortUrl)).thenReturn(Mono.just(originalUrl));
        final URLRequest urlRequest = new URLRequest();
        urlRequest.setUrl(shortUrl);

        webClient.post()
                .uri("/shortener/original-url")
                .bodyValue(urlRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.url").isEqualTo(originalUrl);
    }

    @Test
    void test_get_short_url_by_original_url_but_lock_timeout() {
        final String originalUrl = "https://github.com/scdt-china/interview-assignments";
        when(shortenerApplicationService.getShortUrl(originalUrl))
                .thenReturn(Mono.error(new LockTimeoutException()));
        final URLRequest urlRequest = new URLRequest();
        urlRequest.setUrl(originalUrl);

        webClient.post()
                .uri("/shortener/short-url")
                .bodyValue(urlRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("The process has been locked by another operation, please try again later.")
                .jsonPath("$.error").isEqualTo("LockTimeoutException");
    }

    @Test
    void test_get_short_url_by_original_url_but_short_id_reached_max_limit() {
        final String originalUrl = "https://github.com/scdt-china/interview-assignments";
        when(shortenerApplicationService.getShortUrl(originalUrl))
                .thenReturn(Mono.error(new ShortIdMaximumLimitException()));
        final URLRequest urlRequest = new URLRequest();
        urlRequest.setUrl(originalUrl);

        webClient.post()
                .uri("/shortener/short-url")
                .bodyValue(urlRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("The short ID has reached the maximum length limit.")
                .jsonPath("$.error").isEqualTo("ShortIdMaximumLimitException");
    }

    @Test
    void test_get_original_url_by_short_url_but_not_found() {
        final String shortUrl = "https://shortener.com/s1";
        when(shortenerApplicationService.getOriginalUrl(shortUrl))
                .thenReturn(Mono.error(new ShortenerNotFoundException("s1")));
        final URLRequest urlRequest = new URLRequest();
        urlRequest.setUrl(shortUrl);

        webClient.post()
                .uri("/shortener/original-url")
                .bodyValue(urlRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("The shortener code: [s1] not found.")
                .jsonPath("$.error").isEqualTo("ShortenerNotFoundException");
    }

    @Test
    void test_get_original_url_by_short_url_but_url_is_invalid_format() {
        final String shortUrl = "asdfsdf/s1";
        when(shortenerApplicationService.getOriginalUrl(shortUrl))
                .thenReturn(Mono.error(new URLFormatException()));
        final URLRequest urlRequest = new URLRequest();
        urlRequest.setUrl(shortUrl);

        webClient.post()
                .uri("/shortener/original-url")
                .bodyValue(urlRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("The short url format is invalid.")
                .jsonPath("$.error").isEqualTo("URLFormatException");
    }

    @Test
    void test_get_short_url_by_original_url_but_got_system_exception() {
        final String originalUrl = "https://github.com/scdt-china/interview-assignments";
        when(shortenerApplicationService.getShortUrl(originalUrl)).thenReturn(Mono.defer(() ->
                Mono.error(new SystemException(new RuntimeException("test")))
        ));

        final URLRequest urlRequest = new URLRequest();
        urlRequest.setUrl(originalUrl);

        webClient.post()
                .uri("/shortener/short-url")
                .bodyValue(urlRequest)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.message").isEqualTo("java.lang.RuntimeException: test")
                .jsonPath("$.error").isEqualTo("Internal Server Error");
    }
}