package com.yilong.shorturl.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlDTOTest {

    @Test
    public void testConstruct() {
        UrlDTO urlDTO = new UrlDTO();
    }

    @Test
    public void testGetUrl() {
        UrlDTO urlDTO = new UrlDTO();
        Assertions.assertEquals(null, urlDTO.getUrl());
    }

    @Test
    public void testSetUrl() {
        String longUrl = "https://www.vampire.com/url/looooooong/1";
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl(longUrl);
        Assertions.assertEquals(longUrl, urlDTO.getUrl());
    }
}
