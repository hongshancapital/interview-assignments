package com.example.shorturlservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description
 * @Author xingxing.yu
 * @Date 2022/04/15 16:49
 **/
@ApiModel(description = "系统返回结果实体")
public class SysResult {
    @ApiModelProperty("系统返回数据")
    private Object data;
    @ApiModelProperty("系统返回状态")
    private BStatus bstatus = new BStatus();

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BStatus getBstatus() {
        return bstatus;
    }

    public void setBstatus(int code, String des) {
        this.bstatus.setCode(code);
        this.bstatus.setDes(des);
    }


    @ApiModel(description = "系统返回状态实体")
    public class BStatus {
        @ApiModelProperty("系统返回状态编码")
        int code = 200;
        @ApiModelProperty("系统返回状态信息")
        String des = "success";

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
