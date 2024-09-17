package org.faof.domain;

import org.faof.exceptions.BizException;
import org.faof.exceptions.ExceptionEnum;
import org.faof.exceptions.IApplicationException;

public class ResponseFactory {

    public static BaseResponse<BaseResult> success(String longUrl, String shortUrl) {
        BaseResult result = new BaseResult();
        result.setLongUrl(longUrl);
        result.setShortUrl(shortUrl);
        BaseResponse<BaseResult> response = new BaseResponse<>();
        response.setTimestamp(System.currentTimeMillis());
        response.setResult(result);
        response.setErrorCode(ExceptionEnum.SUCCESS.getErrorCode());
        response.setMsg(ExceptionEnum.SUCCESS.getErrorMessage());
        return response;
    }

    public static BaseResponse<BaseResult> failed(BizException exception) {
        BaseResponse<BaseResult> response = new BaseResponse<>();
        response.setTimestamp(System.currentTimeMillis());
        response.setErrorCode(exception.getErrorCode());
        response.setMsg(exception.getErrorMsg());
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }

}
