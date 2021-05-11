package com.example.shortlink.domain;

import com.example.shortlink.domain.entity.ShortLink;
import com.example.shortlink.domain.service.ShortLinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ShortLinkServiceTest {
    private static final String URL="https://github.com/Netflix/concurrency-limits";
    @Autowired
    private ShortLinkService shortLinkService;
    @Test
    public void should_gen_success_when_correct_url(){
        ShortLink shortUrl=shortLinkService.genShortLink(URL);
        assertTrue(shortUrl.getLongLink()!=null&&!shortUrl.getShortLinkKey().isEmpty());
    }
    @Test
    public void should_gen_fail_when_null_url(){
        ShortLink shortUrl=shortLinkService.genShortLink(null);
        assertTrue(shortUrl.getLongLink()==null||shortUrl.getShortLinkKey().isEmpty());
    }
    @Test
    public void should_get_success_when_correct_url(){
        ShortLink shortUrl=shortLinkService.genShortLink(URL);
        String sourceLink=shortLinkService.getSourceLink(shortUrl.getShortLinkKey());
        assertTrue(!sourceLink.isEmpty());
    }
    @Test
    public void should_get_fail_when_incorrect_url(){
        ShortLink shortUrl=shortLinkService.genShortLink(URL);
        String sourceLink=shortLinkService.getSourceLink(shortUrl.getShortLinkKey());
        assertTrue(!sourceLink.isEmpty());
    }
}
