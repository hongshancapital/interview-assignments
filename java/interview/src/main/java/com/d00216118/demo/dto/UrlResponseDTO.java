package com.d00216118.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 7:48 下午 2021/4/3
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponseDTO {

    private String timestamp;

    private String tinyUrl;

    private String username;

    private String sign;


}
