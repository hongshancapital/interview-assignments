package com.scdt.url.common;

import com.scdt.url.BaseApiTest;
import com.scdt.url.common.exception.ErrorCode;
import com.scdt.url.tiny_url.CreateTinyUrlCommand;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

class CommonApiTest extends BaseApiTest {


    @Test
    void testGetNotFound() {
        client.get()
                .uri("/test_not_exist")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isNotEmpty()
                .jsonPath("$.requestId").isNotEmpty();
    }


    @Test
    void testPostSystemError() {

        ErrorCode error = ErrorCode.SYSTEM_ERROR;
        client.post()
                .uri("/tiny_urls")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isNotEmpty()
                .jsonPath("$.error.code").isEqualTo(error.toString());


    }

    @Test
    void should_bad_request() {

        ErrorCode error = ErrorCode.REQUEST_VALIDATION_FAILED;
        var command = new CreateTinyUrlCommand(null);
        client.post()
                .uri("/tiny_urls")
                .header("x-request-id","test_easten_id")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(command), CreateTinyUrlCommand.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isNotEmpty();

    }
}