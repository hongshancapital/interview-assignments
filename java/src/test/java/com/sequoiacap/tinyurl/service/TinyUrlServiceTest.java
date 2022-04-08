package com.sequoiacap.tinyurl.service;

import com.sequoiacap.tinyurl.exception.DataDuplicationException;
import com.sequoiacap.tinyurl.exception.NotAcceptDataException;
import com.sequoiacap.tinyurl.repository.TinyUrlDao;
import com.sequoiacap.tinyurl.service.impl.TinyUrlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class TinyUrlServiceTest {
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private TinyUrlDao tinyUrlDao;

    @InjectMocks
    private TinyUrlService tinyUrlService = new TinyUrlServiceImpl();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_throw_not_accept_data() {
        doThrow(new DataDuplicationException()).when(tinyUrlDao).save(anyString(), anyString());
        when(tinyUrlDao.queryUrl(anyString())).thenReturn(Optional.empty());
        assertThrows(NotAcceptDataException.class, () -> tinyUrlService.createTinyUrl("http://www.123.com"));
    }

}
