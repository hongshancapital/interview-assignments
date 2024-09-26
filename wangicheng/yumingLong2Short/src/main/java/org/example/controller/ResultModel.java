package org.example.controller;

import java.util.ArrayList;
import java.util.List;

public class ResultModel {
    private int returnCode = 100;
    private String returnMsg = "请求成功";
    private Object data;


    public ResultModel() {
    }


    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
}
