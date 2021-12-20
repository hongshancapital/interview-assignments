package com.example.baiyang.demo.validate;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/17
 * @description: 自定义注解实现逻辑
 */
public class PatternUrlValidator implements ConstraintValidator<PatternUrl, String> {

    //url正则表达式
    public final static String PATTERN = "^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+";

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {

        //入参可以为空
        if (StringUtils.isBlank(url)) {
            return true;
        }

        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(url);

        return matcher.matches();
    }
}
