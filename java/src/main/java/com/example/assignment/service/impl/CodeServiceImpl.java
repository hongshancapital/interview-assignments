package com.example.assignment.service.impl;

import com.example.assignment.Exception.ShortCodeUseOutException;
import com.example.assignment.service.CodeService;
import com.example.assignment.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;

@Service
public class CodeServiceImpl implements CodeService {

    private AtomicLong BASE_CODE = new AtomicLong();

    @Value("${short-url.max.url.length:8}")
    private int length;//short url长度

    private long maxValue;//short url长度对应的最大10进制数

    @PostConstruct
    public void initMaxValue() {
        maxValue = (long) Math.pow((double) 62, (double) length);
    }

    @Override
    public String generateCode() throws ShortCodeUseOutException {
        long num = BASE_CODE.getAndIncrement();
        if (num >= maxValue) {
            throw new ShortCodeUseOutException();
        }
        return ConverterUtil.encode10To62(num, length);
    }
}
