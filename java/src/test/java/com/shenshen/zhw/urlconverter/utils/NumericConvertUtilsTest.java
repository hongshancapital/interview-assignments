package com.shenshen.zhw.urlconverter.utils;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NumericConvertUtilsTest {

    @Test
    void toOtherNumberSystem() {
        SnowFlake snowFlake = new SnowFlake(0, 0);
        NumericConvertUtils.toOtherNumberSystem(-100,62);
        Set<String> list = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            list.add(NumericConvertUtils.toOtherNumberSystem(snowFlake.nextId(), 62));
        }
        Assert.isTrue(list.size() == 100);

    }
}