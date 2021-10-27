package com.example.demo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.regex.Matcher;

@Data
public class QueryShortURLRequest {
    @NotBlank(message = "不能为空")
    @Length(max = 300, message = "最大长度不能超过300个字符")
    @Pattern(regexp = "^(http|https)://([^/:]+)(:\\d*)?(.*)$", message = "长链接必须以http或https打头")
    private String originUrl;

    public static boolean isurl (String pinput){
        String regex = "^(http|https)://([^/:]+)(:\\d*)?(.*)$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        Matcher matcher = p.matcher(pinput);
        return matcher.matches();
    }
}
