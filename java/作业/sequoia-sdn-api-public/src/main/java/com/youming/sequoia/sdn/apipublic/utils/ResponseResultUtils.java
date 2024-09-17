package com.youming.sequoia.sdn.apipublic.utils;


import com.youming.sequoia.sdn.apipublic.constant.ResponseResultMsgEnum;
import com.youming.sequoia.sdn.apipublic.vo.response.ResponseResultVO;
import org.apache.commons.lang3.StringUtils;


//工具类，返回系统信息定义的code和message组成的map
public class ResponseResultUtils {

    public static String getResponseResultStr(ResponseResultMsgEnum responseResultMsgEnum) {
        ResponseResultVO<Object> responseResultVO = ResponseResultUtils.getResponseResult(responseResultMsgEnum);
        String resultStr = JsonTransformUtils.toJson(responseResultVO);
        return resultStr;
    }


    public static <T> ResponseResultVO<T> getResponseResult(ResponseResultMsgEnum responseResultMsgEnum) {
        return getResponseResult(responseResultMsgEnum, null);
    }

    public static <T> ResponseResultVO<T> getResponseResult(ResponseResultMsgEnum responseResultMsgEnum, T t) {
        ResponseResultVO<T> responseResultVO = new ResponseResultVO<T>();
        responseResultVO.setCode(responseResultMsgEnum.getCode());
        responseResultVO.setMsg(responseResultMsgEnum.getMsg());
        responseResultVO.setData(t);
        return responseResultVO;
    }

    /**
     * 附加异常消息的响应对象
     */
    public static <T> ResponseResultVO<T> getResponseResult(ResponseResultMsgEnum responseResultMsgEnum, String msgExtend) {
        return getResponseResult(responseResultMsgEnum, null, msgExtend);
    }


    public static <T> ResponseResultVO<T> getResponseResult(ResponseResultMsgEnum responseResultMsgEnum, T t, String msgExtend) {
        ResponseResultVO<T> responseResultVO = new ResponseResultVO<T>();
        responseResultVO.setCode(responseResultMsgEnum.getCode());
        if (StringUtils.isNoneEmpty(msgExtend))
            responseResultVO.setMsg(responseResultMsgEnum.getMsg() + ";" + msgExtend);
        else
            responseResultVO.setMsg(responseResultMsgEnum.getMsg());
        responseResultVO.setData(t);
        return responseResultVO;
    }

}
