package com.scdt.tinyurl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotBlank;

@Data
@Validated
public class UrlEntity  {


    @NotBlank(message = "URL不能为空")
    @URL(message = "非法的URL")
    private String longUrl;


}
