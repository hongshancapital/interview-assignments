package com.d00216118.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 1:06 下午 2021/3/31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostData extends User{
    @NotBlank
    private String sign;
    @NotBlank
    private String timestamp;

}
