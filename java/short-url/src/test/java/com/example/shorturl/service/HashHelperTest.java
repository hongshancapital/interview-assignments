package com.example.shorturl.service;

import com.example.shorturl.vo.ShortURLIllegalArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HashHelperTest {

    HashHelper helper = new HashHelper();

    @Test
    void hashTo48BitLong() {
        String snull = null;
        assertThrows(IllegalArgumentException.class, () -> helper.hashTo48BitLong(snull));

        // lurl hashed Long is -7674139483528047233
        String lurl = "https://new.qq.com/omn/20210913/20210913A01JME00.html";
        Long hashLong = helper.hashTo48BitLong(lurl);
        Assertions.assertEquals(275756487988607L, hashLong);

        String CTRL_A = "\u0001";
        // lurlCtrlA hashed Long is 8010965872574201235
        String lurlCtrlA = lurl + CTRL_A;
        Long hashCtrlALong = helper.hashTo48BitLong(lurlCtrlA);
        Assertions.assertEquals(188035388931475L, hashCtrlALong);
    }

    @Test
    void convertToBase64URL_IllegalArgument() {
        Long lnull = null;
        assertThrows(IllegalArgumentException.class, () -> helper.convertToBase64URL(lnull));

        Long lnegative = -1L;
        assertThrows(IllegalArgumentException.class, () -> helper.convertToBase64URL(lnegative));

        Long lgreater = 281474976710656L;
        assertThrows(IllegalArgumentException.class, () -> helper.convertToBase64URL(lgreater));
    }


    @Test
    void convertToBase64URL_CorrectCases() {
        String base64URLExpected = "--------";
        String base64URL = helper.convertToBase64URL(0L);
        Assertions.assertEquals(base64URLExpected, base64URL);

        base64URLExpected = "zzzzzzzz";
        base64URL = helper.convertToBase64URL(281474976710655L);
        Assertions.assertEquals(base64URLExpected, base64URL);

        base64URLExpected = "-------8";
        base64URL = helper.convertToBase64URL(9L);
        Assertions.assertEquals(base64URLExpected, base64URL);

        base64URLExpected = "n7y1pMw-";
        base64URL = helper.convertToBase64URL(224916747419392L);
        Assertions.assertEquals(base64URLExpected, base64URL);
    }

    @Test
    void convertToLong_IllegalArgument() {
        String base64URL = null;
        assertThrows(ShortURLIllegalArgumentException.class, () -> helper.convertToLong(base64URL));

        String base64URL_1 = " ";
        assertThrows(ShortURLIllegalArgumentException.class, () -> helper.convertToLong(base64URL_1));

        String base64URL_2 = "@";
        assertThrows(ShortURLIllegalArgumentException.class, () -> helper.convertToLong(base64URL_2));

        String base64URL_3 = "123456789";
        assertThrows(ShortURLIllegalArgumentException.class, () -> helper.convertToLong(base64URL_3));

        String base64URL_4 = "1234abc";
        assertThrows(ShortURLIllegalArgumentException.class, () -> helper.convertToLong(base64URL_4));

        String base64URL_5 = "1234abc@";
        assertThrows(ShortURLIllegalArgumentException.class, () -> helper.convertToLong(base64URL_5));
    }

    @Test
    void convertToLong_CorrectCases() {
        String base64URL = "--------";
        Long value = helper.convertToLong(base64URL);
        Assertions.assertEquals(0L, value);

        base64URL = "zzzzzzzz";
        value = helper.convertToLong(base64URL);
        Assertions.assertEquals(281474976710655L, value);

        base64URL = "-------8";
        value = helper.convertToLong(base64URL);
        Assertions.assertEquals(9L, value);

        base64URL = "n7y1pMw-";
        value = helper.convertToLong(base64URL);
        String binaryString = "110011001000111110000010110101010111111100000000";
        Assertions.assertEquals(Long.parseLong(binaryString, 2), value);

    }
}