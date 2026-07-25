package com.example.domain.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PublicUtilTest {

    @Test
    public void generateShortUuid() {
        log.info(PublicUtil.generateShortUuid());

    }
}