package com.example.surl.utils;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 杨欢
 */
@Data
@NoArgsConstructor
public class Ajax<T> {

    @ApiModelProperty(value = "错误码")
    private int code;
    @ApiModelProperty(value = "信息")
    private String msg;
    @ApiModelProperty(value = "内容")
    private Object data;
    @ApiModelProperty(value = "返回时间")
    private String timestamp;

    // 请求处理成功，并响应结果数据
    public static <T> Ajax<T> success(T data) {
        Ajax<T> resp = new Ajax<>();
        resp.setCode(0);
        resp.setMsg("success");
        resp.setData(data);
        resp.setTimestamp(DateUtil.now());
        return resp;
    }


    public static <T> Ajax<T> error(RuntimeException e) {
        Ajax<T> resp = new Ajax<>();
        resp.setCode(-1);
        resp.setMsg(e.getMessage());
        resp.setTimestamp(DateUtil.now());
        return resp;
    }

}
