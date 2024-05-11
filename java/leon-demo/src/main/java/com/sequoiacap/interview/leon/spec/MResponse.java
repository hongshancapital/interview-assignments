package com.sequoiacap.interview.leon.spec;

import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * description
 *
 * @author leon
 * @since 2021/10/26
 */
@Getter
@Slf4j
public class MResponse<T>{

    private final String rtnCode;

    private final String rtnMsg;

    private T result;

    public MResponse(String rtnCode, String rtnMsg) {
        this.rtnCode = rtnCode;
        this.rtnMsg = rtnMsg;
    }

    public static <T> MResponse<T> success(T rtnData) {
        return buildResponse(MConstants.RTN_CODE_SUCCESS, "success", rtnData);
    }

    public static <T> MResponse<T> failure(String rtnMsg) {
        return buildResponse(MConstants.RTN_CODE_ERROR, rtnMsg, null);
    }

    public static <T> MResponse<T> failure(MException me) {
        return buildResponse(MConstants.RTN_CODE_ERROR, me.getMessage(), null);
    }

    public static <T> MResponse<T> buildResponse(String rtnCode, String rtnMsg, T rtnData) {
        MResponse<T> response = new MResponse<>(rtnCode, rtnMsg);
        if(rtnData!=null){
            response.result = rtnData;
        }
        return response;
    }

    public static <T> void buildHttpResponse(HttpServletResponse response, MResponse<T> mResponse) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter writer = response.getWriter()) {
            String json = JSONUtil.toJsonStr(mResponse);
            writer.print(json);
        } catch (IOException e) {
            log.error("response error", e);
        }
    }
}
