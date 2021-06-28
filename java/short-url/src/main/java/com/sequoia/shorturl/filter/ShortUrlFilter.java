package com.sequoia.shorturl.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 *
 *短域名过滤，前缀不匹配，直接过滤，不调用短域名查询接口
 *
 *@Author xj
 *
 *@Date 2021/06/27
 *
 *@version v1.0.0
 *
 */
@Slf4j
public class ShortUrlFilter extends OncePerRequestFilter {


    private static final String SHORT_URL_PREFIX = "/t/";

	private static final  String SHORT_URL_KEY_PREFIX = "short_url_hash_key_";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = uri.substring(contextPath.length());

		if(!url.startsWith(SHORT_URL_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
    }
}
