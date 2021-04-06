package com.d00216118.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 1:23 下午 2021/4/3
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "This is a URL request object, it is used in the post method")
public class UrlRequestDTO {

    @NotBlank
    private String token;

    @NotBlank
    private String username;

    @NotBlank
    private String url;

//    private String tinyUrl;

    @NotBlank
    private String timestamp;


    @ApiModelProperty(notes = "The signature is used to verify the correctness of the transmission")
    @NotBlank
    private String sign;


}
