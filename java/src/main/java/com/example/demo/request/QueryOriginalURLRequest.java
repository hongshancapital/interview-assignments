package com.example.demo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class QueryOriginalURLRequest {
    @NotBlank(message = "不能为空")
    @Length(max = 8, message = "最大长度不能超过8个字符")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "必须A−Z,a−z,0−9")
    private String shortURL;
}
