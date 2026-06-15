package cn.leirq.demoshorturl.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebFlux
@AutoConfigureWebTestClient
public class UrlControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getShortUrlDefaultDomainTest(){
        String s =  webTestClient
                .get().uri("/getShortUrl?longUrl=https://www.leiruiqi.com/testUrl")
                .exchange()
                .expectStatus().isOk().returnResult(String.class)
                .getResponseBody().blockFirst();
        System.out.println(s);
    }

    @Test
    public void getShortUrlWithDomainTest(){
        String s =  webTestClient
                .get().uri("/getShortUrl?longUrl=https://www.leiruiqi.com/testUrl&shortDomain=ab.xy")
                .exchange()
                .expectStatus().isOk().returnResult(String.class)
                .getResponseBody().blockFirst();
        System.out.println(s);
    }

    @Test
    public void getLongUrlTest(){
        String s =  webTestClient
                .get().uri("/getLongUrl?shortUrl=0006dvGH")
                .exchange()
                .expectStatus().isOk().returnResult(String.class)
                .getResponseBody().blockFirst();
        System.out.println(s);
    }
}
