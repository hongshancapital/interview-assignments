package com.yofei.shortlink.utils;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormatUtil {

    public static String format(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        for (int i = 0; i < fieldErrors.size(); i++) {
            final FieldError fieldError = fieldErrors.get(i);
            sb.append(fieldError.getField())
                .append(" ")
                .append(fieldError.getDefaultMessage());
            if (i != fieldErrors.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }
}