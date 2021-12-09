package com.scdt.url.tiny_url;

import com.scdt.url.BaseApiTest;
import com.scdt.url.common.exception.ErrorCode;
import com.scdt.url.common.util.ShortStringGenerator;
import com.scdt.url.tiny_url.model.TinyUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import static com.scdt.url.tiny_url.model.TinyUrlId.tinyUrlId;

class TinyUrlApiTest extends BaseApiTest {

    @Autowired
    private TinyUrlRepository repository;

    @Test
    void testCreateTinyUrl() {
        var originalUrl = "http://asdsa.test.com/asdadsadsasd";
        var command = new CreateTinyUrlCommand(originalUrl);
        client.post()
                .uri("/tiny_urls")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(command), CreateTinyUrlCommand.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty();
    }

    @Test
    void testCreateTinyUrlTwice() {
        var originalUrl = "http://asdsa.test.com/asdadsadsasd";
        var command = new CreateTinyUrlCommand(originalUrl);
        client.post()
                .uri("/tiny_urls")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(command), CreateTinyUrlCommand.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty();

        client.post()
                .uri("/tiny_urls")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(command), CreateTinyUrlCommand.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty();
    }

    @Test
    void testRetrieveTinyUrl() {
        var originalUrl = "http://asdsa.test.com/asdadsadsasd";
        String shortUrl = ShortStringGenerator.generate(originalUrl,8);
        TinyUrl tinyUrl = TinyUrl.create(tinyUrlId(shortUrl), originalUrl);
        repository.save(tinyUrl);
        String idString = tinyUrl.getId().toString();
        client.get()
                .uri("/tiny_urls/{id}", idString)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.originalUrl").isNotEmpty()
                .jsonPath("$.originalUrl").isEqualTo(originalUrl);
    }


    @Test
    void testNotFoundTinyUrl() {
        ErrorCode error = ErrorCode.TINY_URL_NOT_FOUND;
        String idString = "tinyUrl not exist";
        var id = client.get()
                .uri("/tiny_urls/{id}", idString)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isNotEmpty()
                .jsonPath("$.error.code").isEqualTo(error.toString());
    }
}