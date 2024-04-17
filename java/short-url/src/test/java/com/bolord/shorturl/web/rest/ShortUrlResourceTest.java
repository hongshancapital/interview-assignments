package com.bolord.shorturl.web.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import com.bolord.shorturl.common.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bolord.shorturl.config.ShortUrlConfig;
import com.bolord.shorturl.service.ShortUrlService;

@SpringJUnitWebConfig
@WebFluxTest(controllers = { ShortUrlResource.class })
@Import({ShortUrlConfig.class, ShortUrlService.class})
class ShortUrlResourceTest {

    @Resource
    WebTestClient webTestClient;

    @Test
    void fromShortUrl() throws UnsupportedEncodingException {
        Result result = webTestClient.post()
            .uri("/" + URLEncoder.encode("https://www.baidu.com", StandardCharsets.UTF_8.name()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Result.class)
            .returnResult()
            .getResponseBody();

        String shortUrl = result.getData().toString();

        webTestClient.get()
            .uri(shortUrl)
            .exchange()
            .expectStatus()
            .isFound();
    }

    @Test
    void fromShortUrlNotFound() {
        webTestClient.get()
            .uri("/notFound")
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void fromShortUrlNotFoundWithOverLengthId() {
        webTestClient.get()
            .uri("/idLengthGt8")
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void toShortUrl() throws UnsupportedEncodingException {
        webTestClient.post()
            .uri("/" + URLEncoder.encode("https://www.baidu.com", StandardCharsets.UTF_8.name()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    void toShortUrlThrowRuntimeException() {
        webTestClient.post()
            .uri("/https%3A%2F%2www.baidu.com")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    void toShortUrlWithBadParam() {
        webTestClient.post()
            .uri("/https://www.baidu.com")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void toShortUrlWithBadUrl() throws UnsupportedEncodingException {
        webTestClient.post()
            .uri("/" + URLEncoder.encode("bad .url", StandardCharsets.UTF_8.name()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }
}
