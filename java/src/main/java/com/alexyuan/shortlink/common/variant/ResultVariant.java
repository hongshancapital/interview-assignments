package com.alexyuan.shortlink.common.variant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@ApiModel
@Slf4j
public class ResultVariant {

    /**
     * 自定义服务状态码
     * 返回值语义：
     * "200" : "服务正常"
     * "300" : "参数错误"
     * "400" : "系统错误"
     * "500" : "其他错误"
     */
    @ApiModelProperty("服务状态码")
    private String response_code;

    @ApiModelProperty("短域名")
    private String short_url;

    @ApiModelProperty("长域名")
    private String long_url;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("生成UNIX时间")
    private String generate_time;

    @ApiModelProperty("额外信息")
    private String extra_message;

    public ResultVariant(String short_url, String long_url, String generate_time, String extra_message) {
        this.response_code = "200";
        this.short_url = short_url;
        this.long_url = long_url;
        this.generate_time = generate_time;
        this.extra_message = extra_message;
        this.status = "Success";
    }

    public ResultVariant(String response_code, String short_url, String long_url, String generate_time, String extra_message, String status) {
        this.response_code = response_code;
        this.short_url = short_url;
        this.long_url = long_url;
        this.generate_time = generate_time;
        this.extra_message = extra_message;
        this.status = status;
    }
}
