package com.example.demo.domainnameservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ErrorCode definition.
 *
 * @author laurent
 * @date 2021-12-11 下午3:54
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCode {

    /**
     * success.
     */
    public static final int SUCCESS = 0;
    /**
     * unknown error.
     */
    public static final int UNKNOWN_ERROR = -1;
    /**
     * invalid input.
     */
    public static final int INVALID_INPUT = -100_001;
    /**
     * url encoding fail.
     */
    public static final int ENCODING_FAIL = -100_002;
    /**
     * shortened url not found.
     */
    public static final int URL_NOT_FOUND = -100_003;

}
