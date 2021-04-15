//package com.peogoo.common.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.peogoo.common.constant.AccessPathCheck;
//import com.peogoo.common.error.ResultEnum;
//import com.peogoo.common.util.ApiRes;
//import com.peogoo.common.util.UserIdCache;
//import com.peogoo.user.entity.vo.UserSimpleInfo;
//import com.peogoo.user.service.UserExInfoMangageService;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import sun.misc.BASE64Encoder;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.util.HashMap;
//import java.util.Map;
//
////@SuppressWarnings("ALL")
////@Order(2)
////@Component
////@WebFilter(filterName = "globalFilter", urlPatterns = "/*")
//@Deprecated
//public class RequestFilter implements Filter {
//
//    @Autowired
//    protected StringRedisTemplate stringRedisTemplate;
//
//    private static Logger logger = LoggerFactory.getLogger(RequestFilter.class);
//
//    @Autowired
//    protected UserExInfoMangageService userExInfoMangageService;
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        logger.info("-------------Begin RequestFilter-------------");
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse res = (HttpServletResponse) servletResponse;
//        RequestParameterWrapper requestParameterWrapper = new RequestParameterWrapper(req);
//        Map<String, Object> extraParams = new HashMap<String, Object>();
//
//        // 分辨安卓、苹果端请求
//        String source = req.getHeader("source");
//        if (StringUtils.isNotBlank(source) && "iOS".equals(source)) {
//            extraParams.put("appFlag", "iOS");
//        } else {
//            extraParams.put("appFlag", "Android");
//        }
//
//        // app版本号
//        String appVersion=req.getHeader("appVersion");
//        extraParams.put("appVersion",appVersion);
//
//
//        //特定请求路径不拦截
//        String requestURI = req.getServletPath();
//        if (AccessPathCheck.checkAccessUrl(requestURI)) {
//            String token = req.getHeader("token");
//            String userId = "1";
//            if (StringUtils.isNotEmpty(token)) {
//                userId = stringRedisTemplate.opsForValue().get(token);
//            }
//            extraParams.put("userId", userId);
//            requestParameterWrapper.addParameters(extraParams);
//            filterChain.doFilter(requestParameterWrapper, servletResponse);
//        } else {
//            String token = req.getHeader("token");
//            String grabKey = req.getHeader("grabKey");
//            if (token != null && !("").equals(token)) {
//                String userId = stringRedisTemplate.opsForValue().get(token);
//                //获取用户信息
//                UserSimpleInfo userInfo =null;
//                if (StringUtils.isNotBlank(userId))
//                    userInfo = userExInfoMangageService.getUserInfo(Long.valueOf(userId));
//
//                if ("".equals(userId) || userId == null || userInfo == null) {
//                    PrintWriter writer = null;
//                    try {
//                        writer = res.getWriter();
//                        String result = JSON.toJSONString(ApiRes.getInstance(ResultEnum.TOKEN_ERROR));
//                        writer.write(result);
//                        writer.flush();
//                        writer.close();
//                    } catch (UnsupportedEncodingException e) {
//                        logger.error("tokenAccessFilter Error" + e.getMessage(), e);
//                    } catch (IOException e) {
//                        logger.error("tokenAccessFilter Error:" + e.getMessage(), e);
//                    } finally {
//                        if (null != writer) {
//                            writer.close();
//                        }
//                    }
//                    return;
//                }
//                extraParams.put("userId", userId);
//                //  本地线程存入userid
//                UserIdCache.getInstance().set(Long.valueOf(userId));
//            } else {
//                extraParams.put("userId", "");
//            }
//
//            requestParameterWrapper.addParameters(extraParams);
//            filterChain.doFilter(requestParameterWrapper, servletResponse);
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//}
