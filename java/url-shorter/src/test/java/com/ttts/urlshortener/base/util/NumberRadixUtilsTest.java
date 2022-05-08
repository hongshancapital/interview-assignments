package com.ttts.urlshortener.base.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NumberRadixUtilsTest {

    @ParameterizedTest
    @CsvSource({
        "0, 0",
        "4000, 12W",
        "5000, 1Ie",
        "99999, Q0t",
        "123456789045677, Z3WbxMAn",
        "673456783434566, 35EaEBKOU",
    })
    void decimalToSixtyTwo(long input, String except) {
        String actual = NumberRadixUtils.decimalToSixtyTwo(input);
        assertEquals(except, actual);
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0",
        "4000, 12W",
        "5000, 1Ie",
        "99999, Q0t",
        "123456789045677, Z3WbxMAn",
        "673456783434566, 35EaEBKOU",
    })
    void sixtyTwoToDecimal(long except, String input) {
        long actual = NumberRadixUtils.sixtyTwoToDecimal(input);
        assertEquals(except, actual);
    }

    @ParameterizedTest
    @CsvSource({
        "_123",
        "\"\"",
        "*456",
    })
    void sixtyTwoToDecimal_excep(String input) {
        long actual = NumberRadixUtils.sixtyTwoToDecimal(input);
        System.out.println("--:" + actual);

        //assertThrows(ParameterResolutionException.class, () -> NumberRadixUtils.sixtyTwoToDecimal(input));
    }
}