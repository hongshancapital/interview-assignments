package com.jinblog.shorturl.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecyclingStrategyNoneImplTest {
    RecyclingStrategyNoneImpl recyclingStrategy = new RecyclingStrategyNoneImpl();
    @Test
    void handleAddEvent() {
        recyclingStrategy.handleAddEvent("6");
    }

    @Test
    void handleGetEvent() {
        recyclingStrategy.handleGetEvent("6");
    }

    @Test
    void popGarbage() {
        recyclingStrategy.popGarbage();
    }
}