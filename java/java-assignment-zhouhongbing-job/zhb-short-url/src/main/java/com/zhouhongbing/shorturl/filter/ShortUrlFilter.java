package com.zhouhongbing.shorturl.filter;

import com.alibaba.fastjson.JSON;
import com.zhouhongbing.shorturl.entity.ShorterUrl;
import com.zhouhongbing.shorturl.utils.LRUCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Service   //打开注释可开启过滤器
public class ShortUrlFilter extends GenericFilter {

    @Value("${url.shorturl.prefix}")
    private String shortUrlPrefix;

    @Autowired
    private LRUCache map;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        //完整的项目应该先判断token的有效性
        // String token = exchange.getRequest().getQueryParams().getFirst("authToken");
        String token = "token demo";
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        String uri = request.getRequestURI().toString();
        String uri = request.getRequestURL().toString();

        if (uri.contains("swagger")) {
            //过滤器放入链中,放行
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (uri.startsWith(shortUrlPrefix + "shortUrl")) {
            //过滤器放入链中,放行
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (uri.startsWith(shortUrlPrefix)) {
            //重定向(redirect)到长地址
            if (!StringUtils.isBlank(token)) {
                String shortUrlBeanStr = map.get(uri);
                //如果shortUrlkey不为空或不为空字符串
                if (shortUrlBeanStr != null && !StringUtils.isBlank(shortUrlBeanStr)) {
                    //取出对应的json字符串
                    //将json字符串转化为shorterUrl对象
                    ShorterUrl shorterUrl = JSON.parseObject(shortUrlBeanStr, ShorterUrl.class);
                    //从shorterUrl对象取出longUrl字符串
                    String url = shorterUrl.getLongUrl();

                    //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
                    response.setStatus(HttpStatus.SEE_OTHER.value());
                    response.sendRedirect(url);
                    return;
                }
            }
        } else {
            //过滤器放入链中,放行
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


    }
}