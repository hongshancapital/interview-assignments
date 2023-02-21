package com.wangxiao.shortlink.infrastructure.persisitence;

import com.wangxiao.shortlink.infrastructure.properties.ShortLinkProperties;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

class PersistenceTest {
    @Mock
    ShortLinkProperties shortLinkProperties;
    @InjectMocks
    Persistence persistence;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoad() throws IOException {
        when(shortLinkProperties.getStorePath()).thenReturn("./temp-test");
        Assertions.assertDoesNotThrow(() -> persistence.persist("01AEQ0vU", "longLink"));
        Map<String, String> result = persistence.load();
        Assertions.assertEquals(new HashMap<String, String>() {{
            put("01AEQ0vU", "longLink");
        }}, result);
    }

    @AfterEach
    void cleanStore() {
        when(shortLinkProperties.getStorePath()).thenReturn("./temp-test");
        Files.delete(new File(shortLinkProperties.getStorePath()));
    }
}

