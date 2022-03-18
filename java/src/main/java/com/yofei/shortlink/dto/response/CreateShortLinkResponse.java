package com.yofei.shortlink.dto.response;

import com.yofei.shortlink.dto.BaseResponse;
import lombok.Data;

@Data
public class CreateShortLinkResponse extends BaseResponse {
    private String shortCode;

    public static CreateShortLinkResponse success(String shortCode) {
        CreateShortLinkResponse response = new CreateShortLinkResponse();
        response.success = true;
        response.shortCode = shortCode;
        return response;
    }

    public static CreateShortLinkResponse fail(String errorMsg) {
        CreateShortLinkResponse response = new CreateShortLinkResponse();
        response.success = false;
        response.errorMsg = errorMsg;
        return response;
    }
}
