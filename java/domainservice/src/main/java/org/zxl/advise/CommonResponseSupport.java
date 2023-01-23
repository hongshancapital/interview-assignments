package org.zxl.advise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(annotations = {RestController.class})
public class CommonResponseSupport implements ResponseBodyAdvice<Object> {
    public static final String NOT_TRANSCODING_HEADER = "transcoding_not_allowed";

    /**
     * 这个方法表示对于哪些请求要执行beforeBodyWrite,返回true执行,返回false不执行
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 对于返回的对象如果不是最终对象CommonResponse,则选包装一下
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //响应头部存在"transcoding_not_allowed"字段，则不进行封装返回body原始体
        if (response.getHeaders().containsKey(NOT_TRANSCODING_HEADER)) {
            return body;
        }

        if (!(body instanceof CommonResponse)) {
            CommonResponse commonResponse = CommonResponse.ok(body);
            if (body instanceof String) {
                try {
                    return new ObjectMapper().writeValueAsString(commonResponse);
                } catch (JsonProcessingException e) {
                    return commonResponse;
                }
            }
            return commonResponse;
        }
        return body;
    }
}
