package com.domain.urlshortener.model;

import lombok.Getter;

/**
 * @author: rocky.hu
 * @date: 2022/4/1 22:32
 */
@Getter
public class FieldError {

    private String field;
    private String error;

    public FieldError(String field, String error) {
        this.field = field;
        this.error = error;
    }

    @Override
    public String toString() {
        return "FieldError{" +
                "field='" + field + '\'' +
                ", error='" + error + '\'' +
                '}';
    }

}
