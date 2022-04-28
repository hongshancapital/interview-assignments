package org.goofly.shortdomain.repository.impl;

import org.goofly.shortdomain.repository.ShortDomainRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShortDomainRepositoryTests {

    @Autowired
    private ShortDomainRepository shortDomainRepository;

    @Test
    public void testSave() {
        String shortCode = "aaa";
        String originalUrl = "/a/1190000017412946";
        shortDomainRepository.save(shortCode, originalUrl);


        Assert.assertEquals(shortDomainRepository.getOriginalUrl(shortCode), originalUrl);
    }
}
