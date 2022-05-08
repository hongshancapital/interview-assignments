package com.ttts.urlshortener.business.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.domain.SenderNums;
import com.ttts.urlshortener.repository.SenderNumsRepository;
import org.junit.jupiter.api.Test;

class SenderNumsBusinessImplTest {

    @Test
    void add() {
        SenderNums result = mock(SenderNums.class);

        SenderNumsRepository repository = mock(SenderNumsRepository.class);
        doReturn(result).when(repository).add();

        SenderNumsBusinessImpl target = new SenderNumsBusinessImpl(repository);

        SenderNums actual = target.add();

        assertEquals(result, actual);
    }

    @Test
    void add_exception() {

        SenderNumsRepository repository = mock(SenderNumsRepository.class);
        doThrow(RuntimeException.class).when(repository).add();

        SenderNumsBusinessImpl target = new SenderNumsBusinessImpl(repository);

        assertThrows(BusinessException.class, () -> target.add());
    }
}