package com.lisi.urlconverter.util;

import com.lisi.urlconverter.enumeration.UCErrorType;
import com.lisi.urlconverter.vo.UCResponse;
import constant.CommonConstant;

/**
 * @description: 生成返回类的工具类
 * @author: li si
 */
public class ResponseUtil {
    public static UCResponse buildSuccessResponse(Object data){
        UCResponse response = new UCResponse();
        response.setRespCode(CommonConstant.SUCCESS_RESPONSE_CODE);
        response.setData(data);
        return response;
    }

    public static UCResponse buildErrorResponse(UCErrorType ucErrorType){
        UCResponse response = new UCResponse();
        response.setRespCode(CommonConstant.FAILED_RESPONSE_CODE);
        response.setErrCode(ucErrorType.getErrCode());
        response.setErrMsg(ucErrorType.getErrMsg());
        return response;
    }

    public static UCResponse builderErrorResponse(String respCode, String errMsg){
        UCResponse response = new UCResponse();
        response.setRespCode(respCode);
        response.setErrMsg(errMsg);
        return response;
    }
}
