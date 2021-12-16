package com.scdt.china.shorturl.repository;

import com.scdt.china.shorturl.configuration.ApplicationProperties;
import com.scdt.china.shorturl.repository.id.RandomIdGenerator;
import com.scdt.china.shorturl.repository.id.SnowflakeIdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;

public class InMemoryUrlRepositoryTest {

    private InMemoryUrlRepository inMemoryUrlRepository;

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException {
        ApplicationProperties properties = new ApplicationProperties();
        properties.setBaseUrl("https://s.scdt.com/");
        properties.setMemoryStoreCount(10L);
        properties.setBaseTime(System.currentTimeMillis());
        inMemoryUrlRepository = new InMemoryUrlRepository(new RandomIdGenerator(1, 1000000), properties);
    }

    @Test
    public void saveAndGet() {
        String expectedUrl = "https://news.scdt.com";
        Long urlId = inMemoryUrlRepository.save(expectedUrl);
        String url = inMemoryUrlRepository.get(urlId);
        Assertions.assertEquals(expectedUrl, url);
    }

    @Test
    public void saveAndGetRemoval() {
        String expectedUrl = "https://news.scdt.com";
        Long urlId = inMemoryUrlRepository.save(expectedUrl);
        String url = inMemoryUrlRepository.get(urlId);
        Assertions.assertEquals(expectedUrl, url);

        Assertions.assertTimeout(Duration.ofSeconds(1), () -> {
            while (true) {
                inMemoryUrlRepository.save(expectedUrl);
                String urlRemoved = inMemoryUrlRepository.get(urlId);
                if (urlRemoved == null) {
                    break;
                }
            }
        });
    }

}