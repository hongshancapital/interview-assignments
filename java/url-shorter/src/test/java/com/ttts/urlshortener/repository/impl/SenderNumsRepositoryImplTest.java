package com.ttts.urlshortener.repository.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.ttts.urlshortener.domain.SenderNums;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class SenderNumsRepositoryImplTest {
    @Test
    public void add() {
        SenderNumsRepositoryImpl target = new  SenderNumsRepositoryImpl();

        SenderNums actual = target.add();

        log.debug("申请结果: {}", actual);
    }
}