package com.example.demo.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="交互结果返回对象", description="公共返回结果")
public class Response<T> implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @Builder.Default
   @ApiModelProperty(value="状态码，成功0，失败非0", required = true)
   private int code = 0;
   
   @ApiModelProperty(value="描述信息", required = true)
   private String message;
   
   @ApiModelProperty(value="成功时返回的数据", required = false)
   private T data;
}
