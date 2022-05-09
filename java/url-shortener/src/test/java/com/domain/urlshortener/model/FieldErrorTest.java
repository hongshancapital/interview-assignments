package com.domain.urlshortener.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 2:48
 */
public class FieldErrorTest {

    @Test
    public void test_constructor() {
        new FieldError("a", "b");
    }

    @Test
    public void test_getField() {
        FieldError fieldError = new FieldError("a", "b");
        Assertions.assertEquals("a", fieldError.getField());
    }

    @Test
    public void test_getError() {
        FieldError fieldError = new FieldError("a", "b");
        Assertions.assertEquals("b", fieldError.getError());
    }

    @Test
    public void test_toString() {
        FieldError fieldError = new FieldError("a", "b");
        System.out.println(fieldError.toString());
    }

}
