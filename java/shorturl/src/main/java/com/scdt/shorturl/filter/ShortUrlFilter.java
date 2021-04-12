package com.scdt.shorturl.filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.scdt.shorturl.service.ShortUrlService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class ShortUrlFilter extends OncePerRequestFilter{
	private static final Logger log=LoggerFactory.getLogger(ShortUrlFilter.class);
	private static final String SHORT_URL_PREFIX="/t/";
	
	@Value("${spring.application.name}")private String applicationName;
	@Autowired private ShortUrlService shortUrlService;

	/**
	 * 过滤所有的请求并进行验证和处理，如果是合法有效短地址请求则将62进制的短地址转换为10进制的token再获取对应长地址，并自动跳转过去。
	 * @author 周小建
	 * @param request
	 * @param response
	 * @param filterChain
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException{
		String url=request.getRequestURL().toString();
		String uri=request.getRequestURI();
		//String urlPrefix=url.substring(0,url.indexOf(uri));
		//String contextPath=request.getContextPath();
		//String url=uri.substring(contextPath.length());
		String shortUrlPrefix="/"+applicationName+SHORT_URL_PREFIX;
		if(!uri.startsWith(shortUrlPrefix)){
			filterChain.doFilter(request,response);
			return;
		}
		String shortUrl=uri.replaceFirst(shortUrlPrefix,"");
		String originalUrl=shortUrlService.short2long(shortUrl);
		if(StringUtils.isEmpty(originalUrl)){
			log.warn("对应url{},没有找到原链接",url);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			PrintWriter out=response.getWriter();
			out.write("{\"flag\":\"error\",\"msg\":\"无效短地址\",\"data\":"+url+"}");
			out.flush();
			return;
		}
		/*
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.write("<!DOCTYPE html>\n"+"<html lang=\"en\">\n"+"<head>\n"+"    <meta charset=\"UTF-8\">\n"+"    <title>Title</title>\n"+"</head>\n"+"<body>\n"+"<script>\n"+"    window.location.href = '"+originalUrl+"'"+"</script>\n"+"\n"+"</body>\n"+"</html>");
		out.flush();
		*/
		String fullOriginalUrl="/"+applicationName+"/example/"+(originalUrl==null?"aaa":originalUrl)+"?shortUrl="+url;
		request.getRequestDispatcher(fullOriginalUrl).forward(request,response);
	}
}