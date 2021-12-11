package com.example.demo.domainnameservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * integer and string mapping util.
 *
 * @author laurent
 * @date 2021-12-11 下午1:22
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncodeUtil {

    private static final Integer DICTIONARY_SIZE = 62;

    private static final Integer SHORT_URL_LENGTH = 8;

    /**
     * shortened url character range.
     */
    private static final String[] VALUES = new String[]{
            "0","1","2","3","4","5","6","7","8","9",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
    };
    /**
     * int -> String mapping.
     */
    private static final Map<Integer, String> DICTIONARY = new HashMap<>(DICTIONARY_SIZE);

    static {
        for (int i = 0; i < VALUES.length; i ++) {
            DICTIONARY.put(i, VALUES[i]);
        }
    }

    /**
     * generate String by Integer, using DICTIONARY.
     * @param code  integer coding.
     * @return  String.
     * fixed length of 8. first 6 of ${code} mode by 62(DICT size), last 2 of timestamps MODE by 62(take last 2 characters).
     */
    public static String encode(Integer code) {
        StringBuilder builder = new StringBuilder(SHORT_URL_LENGTH);
        // avoiding overflow.
        if (code < 0) {
            code = -(code + 1);
        }
        for (int i = 0; i < 6; i ++) {
            builder.append(DICTIONARY.getOrDefault(code % DICTIONARY_SIZE, ""));
            code /= DICTIONARY_SIZE;
        }
        int timestamps = (int) (System.currentTimeMillis() / 1000L);
        for (int i = 0; i < 2; i ++) {
            builder.append(DICTIONARY.getOrDefault(timestamps % DICTIONARY_SIZE, ""));
            timestamps /= DICTIONARY_SIZE;
        }
        return builder.toString();
    }


}
