package com.yofei.shortlink.dto.response;

import com.yofei.shortlink.dto.BaseResponse;
import lombok.Data;

@Data
public class QueryShortLinkResponse extends BaseResponse {
    private String url;

    public static QueryShortLinkResponse success(String url) {
        QueryShortLinkResponse response = new QueryShortLinkResponse();
        response.success = true;
        response.url = url;
        return response;
    }

    public static QueryShortLinkResponse fail(String errorMsg) {
        QueryShortLinkResponse response = new QueryShortLinkResponse();
        response.success = false;
        response.errorMsg = errorMsg;
        return response;
    }
}
