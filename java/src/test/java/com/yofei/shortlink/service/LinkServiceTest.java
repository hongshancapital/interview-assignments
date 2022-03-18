package com.yofei.shortlink.service;

import com.yofei.shortlink.dao.entity.LinkEntity;
import com.yofei.shortlink.dao.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class LinkServiceTest {

    @Mock
    private LinkRepository linkRepository;

    @InjectMocks
    private LinkService linkService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_throw_not_accept_data() {

        when(linkRepository.findByMd5Equals(anyString())).thenReturn(LinkEntity.builder().md5("a").url("http://www.123.com").build());

        assertEquals("d", linkService.create("http://www.123.com"));
    }

}
