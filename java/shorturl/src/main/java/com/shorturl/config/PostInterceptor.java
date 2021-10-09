package com.shorturl.config;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSONObject;
import com.shorturl.entity.ResponseDto;
import com.shorturl.enums.ResponseEnum;
import com.shorturl.util.ResponseUtil;
import com.shorturl.util.SignUtil;

@Component
public class PostInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	response.setCharacterEncoding("utf-8");
        String token = request.getParameter("access_token");
        if (null != token) {
            //验证token是否正确
            boolean result = SignUtil.verify(token);
            if (result) {
                return true;
            }
        }
        //token验证不通过
        ResponseDto responseDto = ResponseUtil.getResponseDto(ResponseEnum.AUTH_ERROR);
        responseMessage(response,response.getWriter(),responseDto);
        return false;
    }
    
    /**
     * @param response
     * @param out
     * @param ResponseDto
     */
    private void responseMessage(HttpServletResponse response, PrintWriter out, ResponseDto responseDto) {
        response.setContentType("application/json; charset=utf-8");
        out.print(JSONObject.toJSONString(responseDto));
        out.flush();
        out.close();
    }
}
