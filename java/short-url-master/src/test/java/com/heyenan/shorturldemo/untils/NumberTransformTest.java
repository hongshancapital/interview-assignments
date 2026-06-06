package com.heyenan.shorturldemo.untils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


public class NumberTransformTest {
    @Test
    @DisplayName("10进制和62进制互换")
    void convert10to62() {
        String result = NumberTransform.convert10to62(35174605, 6);
        String result1 = NumberTransform.convertBase62ToDecimal("2NaWL");
        assertThat(result, equalTo("0C0fQm"));
        assertThat(result1, equalTo("35174605"));

    }
}
