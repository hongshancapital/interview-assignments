package com.d00216118.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 6:26 下午 2021/4/4
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TinyUrlResponseDTO {

    private String timestamp;

    private String url;

    private String username;

    private String sign;

}
