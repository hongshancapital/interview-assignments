package interview.assignments.zhanggang.core.shortener.adapter.api;

import interview.assignments.zhanggang.core.shortener.adapter.api.request.URLRequest;
import interview.assignments.zhanggang.core.shortener.application.ShortenerApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ShortenerController.class)
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
                .expectStatus().is2xxSuccessful()
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
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.url").isEqualTo(originalUrl);
    }
}