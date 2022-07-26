package com.scdt.url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class BaseApiTest {

    protected WebTestClient client;

    @BeforeEach
    public void init(ApplicationContext context) {

        client = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .defaultHeader("name", "easten")
                .build();
    }
}
