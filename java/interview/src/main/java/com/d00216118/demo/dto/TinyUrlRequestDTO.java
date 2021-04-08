package com.d00216118.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 4:44 下午 2021/4/4
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TinyUrlRequestDTO {

    @NotBlank
    private String token;

    @NotBlank
    private String username;

    @NotBlank
    private String tinyUrl;

    @NotBlank
    private String timestamp;

    @NotBlank
    private String sign;
}
