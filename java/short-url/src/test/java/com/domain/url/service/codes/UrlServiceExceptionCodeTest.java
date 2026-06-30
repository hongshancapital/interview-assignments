package com.domain.url.service.codes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlServiceExceptionCodeTest {

    @Test
    public void getMessage() {
        assertNotNull(UrlServiceExceptionCode.__URL_E_0001__.getMessage());
    }
}