package com.scdt.shorturl.service;

import com.scdt.shorturl.App;
import com.scdt.shorturl.model.Res;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "360000")
@SpringBootTest(classes = App.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class URLControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void testPostUrl() {
    String longUrl1 = "https://github.com/scdt-china/interview-assignments1";
    String longUrl2 = "https://github.com/scdt-china/interview-assignments2";

    webTestClient
      .post().uri("/short-urls/api/v1/createShortUrl")
            .body(BodyInserters.fromValue(List.of(longUrl1,longUrl2)))
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isCreated()
      .expectBody(Res.class).value(record -> {
        assertThat(record.isSuccess()).isEqualTo(true);
    });
  }

    @Test
    public void testPostUrl4() {
        String longUrl1 = " https://github.com/scdt-china/interview-assignments1";
        String longUrl2 = " https://github.com/scdt-china/interview-assignments2";

        webTestClient
                .post().uri("/short-urls/api/v1/createShortUrl")
                .body(BodyInserters.fromValue(List.of(longUrl1,longUrl2)))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Res.class).value(record -> {
                    assertThat(record.isSuccess()).isEqualTo(false);
                });
    }

  @Test
  public void testPostUrl2() {
    String longUrl1 = " https://github.com/scdt-china/interview-assignments1";
    String longUrl2 = "2阿三大苏打实打实大苏打倒萨啊实打实打算阿萨大大飒飒大苏打撒旦阿萨大大飒飒大苏打阿三大苏打实打实的阿三大苏打实打实的阿三大苏打实打实的阿三大苏打实打实的阿三大苏打实打实的啊实打实大苏打撒" +
            "旦啊实打实大苏打撒旦啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打撒打算啊实打实大苏打实打实大苏打实打实的啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实打算啊实打实大苏打实打实大苏打实打实大苏打啊实打" +
            "实大苏打实打实大苏打盛大的阿三大苏打实打实大苏打实打实大苏打撒旦啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实的阿德飒飒大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实的啊实打实的撒大苏打撒旦" +
            "啊实打实大苏打撒旦啊实打实大苏啊实打实大苏打撒旦啊实打实大苏打啊飒飒大苏打大" +
            "啊实打实大苏打实打实大苏打实打实啊实打实大苏打实打实大苏打撒旦啊实打实大苏打实打实大苏打大苏打实打实啊实打实大苏打撒大苏打" +
            "啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打的是啊实打实大苏打实打实大苏打盛大的" +
            "啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实的撒大大啊实打实阿萨撒旦撒大苏打实打实大苏打撒旦啊实打实大苏打撒旦" +
            "旦啊实打实大苏打撒旦啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打撒打算啊实打实大苏打实打实大苏打实打实的啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实打算啊实打实大苏打实打实大苏打实打实大苏打啊实打" +
            "实大苏打实打实大苏打盛大的阿三大苏打实打实大苏打实打实大苏打撒旦啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实的阿德飒飒大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实的啊实打实的撒大苏打撒旦" +
            "啊实打实大苏打撒旦啊实打实大苏啊实打实大苏打撒旦啊实打实大苏打啊飒飒大苏打大" +
            "啊实打实大苏打实打实大苏打实打实啊实打实大苏打实打实大苏打撒旦啊实打实大苏打实打实大苏打大苏打实打实啊实打实大苏打撒大苏打" +
            "啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打的是啊实打实大苏打实打实大苏打盛大的" +
            "啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实的撒大大啊实打实阿萨撒旦撒大苏打实打实大苏打撒旦啊实打实大苏打撒旦" +
            "旦啊实打实大苏打撒旦啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打撒打算啊实打实大苏打实打实大苏打实打实的啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实打算啊实打实大苏打实打实大苏打实打实大苏打啊实打" +
            "实大苏打实打实大苏打盛大的阿三大苏打实打实大苏打实打实大苏打撒旦啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实的阿德飒飒大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实的啊实打实的撒大苏打撒旦" +
            "啊实打实大苏打撒旦啊实打实大苏啊实打实大苏打撒旦啊实打实大苏打啊飒飒大苏打大" +
            "啊实打实大苏打实打实大苏打实打实啊实打实大苏打实打实大苏打撒旦啊实打实大苏打实打实大苏打大苏打实打实啊实打实大苏打撒大苏打" +
            "啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打的是啊实打实大苏打实打实大苏打盛大的" +
            "啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实的撒大大啊实打实阿萨撒旦撒大苏打实打实大苏打撒旦啊实打实大苏打撒旦" +
            "旦啊实打实大苏打撒旦啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打撒打算啊实打实大苏打实打实大苏打实打实的啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实打算啊实打实大苏打实打实大苏打实打实大苏打啊实打" +
            "实大苏打实打实大苏打盛大的阿三大苏打实打实大苏打实打实大苏打撒旦啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实的阿德飒飒大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实的啊实打实的撒大苏打撒旦" +
            "啊实打实大苏打撒旦啊实打实大苏啊实打实大苏打撒旦啊实打实大苏打啊飒飒大苏打大" +
            "啊实打实大苏打实打实大苏打实打实啊实打实大苏打实打实大苏打撒旦啊实打实大苏打实打实大苏打大苏打实打实啊实打实大苏打撒大苏打" +
            "啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打的是啊实打实大苏打实打实大苏打盛大的" +
            "啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实的撒大大啊实打实阿萨撒旦撒大苏打实打实大苏打撒旦啊实打实大苏打撒旦" +
            "旦啊实打实大苏打撒旦啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打撒打算啊实打实大苏打实打实大苏打实打实的啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实打算啊实打实大苏打实打实大苏打实打实大苏打啊实打" +
            "实大苏打实打实大苏打盛大的阿三大苏打实打实大苏打实打实大苏打撒旦啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实的阿德飒飒大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打实打实的啊实打实的撒大苏打撒旦" +
            "啊实打实大苏打撒旦啊实打实大苏啊实打实大苏打撒旦啊实打实大苏打啊飒飒大苏打大" +
            "啊实打实大苏打实打实大苏打实打实啊实打实大苏打实打实大苏打撒旦啊实打实大苏打实打实大苏打大苏打实打实啊实打实大苏打撒大苏打" +
            "啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实大苏打的是啊实打实大苏打实打实大苏打盛大的" +
            "啊实打实大苏打实打实大苏打实打实大苏打啊实打实大苏打实打实的撒大大啊实打实阿萨撒旦撒大苏打实打实大苏打撒旦啊实打实大苏打撒旦" +
            "打撒旦啊实打实大苏打撒旦啊实打实大苏打实打实的啊实打" +
            "实大苏打撒旦撒大苏打实打实大苏打啊实打实大苏打撒大苏打https://github.com/scdt-china/interview-assignments2";

    webTestClient
            .post().uri("/short-urls/api/v1/createShortUrl")
            .body(BodyInserters.fromValue(List.of(longUrl1,longUrl2)))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Res.class).value(record -> {
              assertThat(record.isSuccess()).isEqualTo(false);
            });
  }

  @Test
  public void testPostUrl3() {

    webTestClient
            .post().uri("/short-urls/api/v1/createShortUrl")
            .body(BodyInserters.fromValue(new HashSet()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Res.class).value(record -> {
              assertThat(record.isSuccess()).isEqualTo(false);
            });
  }

  @Test
  public void testGetUrl() {
    String shortUrl1 = "BgqrNq5K";
    String shortUrl2 = "pBLbgMDv";
    webTestClient
      .post().uri("/short-urls/api/v1/getLongUrl")
            .body(BodyInserters.fromValue(List.of(shortUrl1,shortUrl2)))
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      .expectBody(Res.class).value(record -> {
        assertThat(record.getCode()).isEqualTo(Res.ErrorCode.OK.getCode());
      });
  }

  @Test
  public void testGetUrl2() {
    String shortUrl1 = "pBL/MDvs";
    String shortUrl2 = "pBLbgMDvssadasd";
    webTestClient
            .post().uri("/short-urls/api/v1/getLongUrl")
            .body(BodyInserters.fromValue(List.of(shortUrl1,shortUrl2)))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Res.class).value(record -> {
              assertThat(record.getCode()).isEqualTo(Res.ErrorCode.INVALID.getCode());
            });
  }

  @Test
  public void testGetUrl3() {

    webTestClient
            .post().uri("/short-urls/api/v1/getLongUrl")
            .body(BodyInserters.fromValue(new HashSet()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Res.class).value(record -> {
              assertThat(record.getCode()).isEqualTo(Res.ErrorCode.INVALID.getCode());
            });
  }
}