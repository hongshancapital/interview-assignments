package com.yilong.shorturl.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UrlDTO {

    @NotBlank(message = "Invalid url")
    @URL(message = "Invalid url")
    private String url;
}
