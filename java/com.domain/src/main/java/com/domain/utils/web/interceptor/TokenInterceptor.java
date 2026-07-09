package com.domain.utils.web.interceptor;

import com.domain.config.GlobalParametersConfig;
import com.domain.utils.web.annotation.ApiPermission;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

/**
 * 安全 token验证拦截器
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
public class TokenInterceptor implements HandlerInterceptor {

    public final static String HTTP_HEAD_AUTHOR = "Authorization";  //HTTP HEAD KEY

    public final static String ACCESS_TOKEN_START_INDEX = "Bearer";  //aaccess_token index

    private GlobalParametersConfig globalParametersConfig; //全局动态参数

    public TokenInterceptor(GlobalParametersConfig globalParametersConfig){
        this.globalParametersConfig=globalParametersConfig;
    }

    /**
     * 拦截HTTP请求，验证其合法性
     * @param  request
     * @return accessToen
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)  {
        if(isNeedCheckToken(request,handler)){  //需要验证token
            String accessToken=extractHeaderToken(request);
            if(StringUtils.isEmpty(accessToken)){  //无token 401
                outPrintUnauthorized(response);
                return false;
            }
            if(!checkToken(accessToken)){  //token验证失败 401
                outPrintUnauthorized(response);
                return false;
            }
        }

        return true;
    }
    /**
     * http响应无权限访问
     * @param  response
     */
    private void outPrintUnauthorized(HttpServletResponse response){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
    /**
     * 验证token
     * @param  accessToken
     * @return true 验证通过 false 验证不通过
     */
    private Boolean checkToken(String accessToken){
        if(accessToken.equals(globalParametersConfig.getAccessToken()))return true;
        return false;
    }
    /**
     * 是否需要验证token
     * @param  request
     * @return true 需要验证 false不需要验证
     */
    private Boolean isNeedCheckToken(HttpServletRequest request,Object handler){
        String requestUrl=request.getServletPath();
        if(!globalParametersConfig.getAuthEnabled().equals("true")) return false;
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod)handler;
            ApiPermission permission = method.getMethodAnnotation(ApiPermission.class);
            if(permission==null) return false;
        }
        Set<String> excludeUrls=globalParametersConfig.getAuthExcludeUrls();
        if(excludeUrls!=null){
            Iterator<String> it = excludeUrls.iterator();
            while (it.hasNext()) {
                String excludeUrl = it.next();
                if(requestUrl.startsWith(excludeUrl))return false;
            }
        }
        return true;
    }
    /**
     *  HTTP请求头中解析token
     * @param  request
     * @return accessToen
     */
    private String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(HTTP_HEAD_AUTHOR);
        String value;
        do {
            if(!headers.hasMoreElements()) {
                return null;
            }

            value = (String)headers.nextElement();
        } while(!value.toLowerCase().startsWith(ACCESS_TOKEN_START_INDEX.toLowerCase()));
        String authHeaderValue = value.substring(ACCESS_TOKEN_START_INDEX.length()).trim();
        return authHeaderValue;
    }
}
