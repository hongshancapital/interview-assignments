package com.skyscreen.urldemo.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PatternUtilsTest {

    @Test
    public void checkIfValidUrl(){

        Assertions.assertTrue(PatternUtils.checkIfValidUrl("http://www.baidu.com"));
        Assertions.assertFalse(PatternUtils.checkIfValidUrl("htp://www.baidu.com"));

    }
}
