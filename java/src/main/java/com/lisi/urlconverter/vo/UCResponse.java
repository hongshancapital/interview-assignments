package com.lisi.urlconverter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 封装域名转换服务返回的信息
 * @author: li si
 */
@ApiModel("返回给前端的类")
public class UCResponse {
    @ApiModelProperty("返回码，1000-正常，1001-系统内部异常，9501-传参异常")
    private String respCode;

    @ApiModelProperty("返回信息")
    private Object data;

    @ApiModelProperty("错误码，respCode为1001时生效")
    private String errCode;

    @ApiModelProperty("错误详细信息，respCode为1001时生效")
    private String errMsg;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
