package com.redwood.urlshorter;

import com.redwood.urlshorter.api.Controller;
import com.redwood.urlshorter.model.NewUrl;
import com.redwood.urlshorter.model.SavedShortUrl;
import com.redwood.urlshorter.repositories.ShortUrl;
import com.redwood.urlshorter.services.UrlShortenerService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DirtiesContext
public class ControllerTest {

    private WebTestClient webTestClient;

    @Autowired
    private UrlShortenerService service;

    @Autowired
    private Controller controller;

    @LocalServerPort
    private int randomServerPort;

    @BeforeEach
    public void setup() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + randomServerPort).build();
        ReflectionTestUtils.setField(controller, "service", service);
    }

    @Test
    void GIVEN_valid_url_WHEN_calling_put_THEN_return_ok_and_ShortUrl_in_response() {

        final NewUrl newUrl = new NewUrl();
        newUrl.setUrl("https://www.sequoiacap.cn/bb705a56f620");
        SavedShortUrl shortUrl = service.put(newUrl);

        webTestClient.post()
                .uri(UrlShortenerApplication.BASE_PATH)
                .bodyValue(shortUrl)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody(ShortUrl.class)
                .value(device -> assertNotNull(device.getUrl()))
                .value(device -> assertNotNull(device.getKey()));
    }

    @Test
    void GIVEN_url_too_short_WHEN_calling_create_THEN_return_BAD_REQUEST() {

        final NewUrl shortUrl = new NewUrl();
        shortUrl.setUrl("12345");
        webTestClient.post()
                .uri(UrlShortenerApplication.BASE_PATH)
                .bodyValue(shortUrl)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void GIVEN_url_too_long_WHEN_calling_create_THEN_return_BAD_REQUEST() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2048; i++) {
            sb.append("!");
        }
        final NewUrl shortUrl = new NewUrl().setUrl("https://www.sequoiacap.cn/" + sb.toString());
        webTestClient.post()
                .uri(UrlShortenerApplication.BASE_PATH)
                .bodyValue(shortUrl)
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    void GIVEN_empty_url_WHEN_calling_create_THEN_return_BAD_REQUEST() {

        final NewUrl shortUrl = new NewUrl();
        shortUrl.setUrl("");
        webTestClient.post()
                .uri(UrlShortenerApplication.BASE_PATH)
                .bodyValue(shortUrl)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void GIVEN_null_url_WHEN_calling_create_THEN_return_BAD_REQUEST() {

        final NewUrl shortUrl = new NewUrl();
        shortUrl.setUrl(null);
        webTestClient.post()
                .uri(UrlShortenerApplication.BASE_PATH)
                .bodyValue(shortUrl)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void GIVEN_valid_url_WHEN_saved_and_read_THEN_expect_saved_equals_reading() {
        final NewUrl shortUrl = new NewUrl();
        shortUrl.setUrl("https://www.sequoiacap.cn/1.html");
        SavedShortUrl savedShortUrl = controller.create(shortUrl).getBody();
        SavedShortUrl readedShortUrl = controller.listById(savedShortUrl.getKey());
        Assert.assertEquals(savedShortUrl.getUrl(), readedShortUrl.getUrl());
    }
}
