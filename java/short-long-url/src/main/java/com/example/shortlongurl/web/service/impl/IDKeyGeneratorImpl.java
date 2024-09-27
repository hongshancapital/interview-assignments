package com.example.shortlongurl.web.service.impl;

import com.example.shortlongurl.web.service.IDKeyGenerator;

public class IDKeyGeneratorImpl implements IDKeyGenerator {
    @Override
    public long getNextId() {
        return System.currentTimeMillis();
    }
}
