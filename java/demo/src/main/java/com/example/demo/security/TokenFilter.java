package com.example.demo.security;

import com.example.demo.common.Result;
import com.example.demo.common.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dsm
 * @Description: 权限拦截器
 * @Date Create in 2021/12/23
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Resource
    private ObjectMapper objectMapper;

    private List <String> urllsit=new ArrayList <>();

    @PostConstruct
    public void setUrllsit() {
        urllsit.add("/v2/api-docs");
        urllsit.add("/swagger-resources");
        urllsit.add("/swagger-ui.html");
        urllsit.add("/webjars");
        urllsit.add("/demo-service/doc.html");
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        String requestUrl=request.getRequestURI();
        boolean b=false;
        for (String url : urllsit) {
            if (requestUrl.indexOf(url) != -1) {
                b=true;
            }
        }
        if (!b) {
            String token=request.getHeader("Authorization");
            if (!StringUtils.hasText(token)) {
                Result result=new Result <String>();
                result.setResult(ResultCode.TOKEN_IS_BLANK.isSuccess(), ResultCode.TOKEN_IS_BLANK.getCode(), ResultCode.TOKEN_IS_BLANK.getMessage());
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().write(objectMapper.writeValueAsString(result));
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

