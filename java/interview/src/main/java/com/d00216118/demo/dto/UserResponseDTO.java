package com.d00216118.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 7:42 下午 2021/4/3
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private  String token;

    private String timestamp;

    private String username;

    private String sign;

}
