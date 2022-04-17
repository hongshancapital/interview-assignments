package com.yilong.shorturl.service;

import com.yilong.shorturl.common.exception.BusinessException;
import com.yilong.shorturl.dao.UrlDao;
import com.yilong.shorturl.service.impl.UrlServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UrlServiceMockTest {

    @InjectMocks
    private UrlServiceImpl urlServiceImpl;

    @Mock
    private UrlDao urlDao;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveWhenStorageFull() {
        String longUrl = "https://www.vampire.com/url/looooooong/1";
        Mockito.when(urlDao.saveUrl(longUrl)).thenReturn(null);
        Assertions.assertThrows(BusinessException.class, () -> urlServiceImpl.saveOriginUrl(longUrl));
    }
}
