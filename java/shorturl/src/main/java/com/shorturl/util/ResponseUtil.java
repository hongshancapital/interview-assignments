package com.shorturl.util;

import com.shorturl.entity.ResponseDto;
import com.shorturl.enums.ResponseEnum;

/**
 * Api组装返回信息工具类
 * @author shaochengming
 * @date 2021/10/15
 */
public class ResponseUtil {

	/**
     * @param data
     * @return ResponseDto
     */
    public static ResponseDto getResponseDto(Object data) {
        return getResponseDto(data, ResponseEnum.SUCCESS.getErrCode(), ResponseEnum.SUCCESS.getErrMsg());
    }

    /**
     * @param code
     * @param msg
     * @return ResponseDto
     */
    public static ResponseDto getResponseDto(int code,String msg) {
        return getResponseDto(null, code, msg);
    }

    /**
     * @param ResponseEnum
     * @return ResponseDto
     */
    public static ResponseDto getResponseDto(ResponseEnum ResponseEnum){
        return  getResponseDto(ResponseEnum.getErrCode(),ResponseEnum.getErrMsg());
    }


    public static ResponseDto getResponseDto(Object data, int code, String msg) {
        ResponseDto ResponseDto = new ResponseDto(data);
        ResponseDto.setErrCode(code);
        ResponseDto.setErrMsg(msg);
        return ResponseDto;
    }
}
