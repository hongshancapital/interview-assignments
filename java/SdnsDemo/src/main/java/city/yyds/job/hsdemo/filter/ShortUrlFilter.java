package city.yyds.job.hsdemo.filter;

import city.yyds.job.hsdemo.mdb.DnsMemoryDB;
import city.yyds.job.hsdemo.utils.ConstUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
public class ShortUrlFilter extends OncePerRequestFilter {


    private static final Logger log = LoggerFactory.getLogger(ShortUrlFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = uri.substring(contextPath.length());
        if(!url.startsWith(ConstUtil.SHORT_URL_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String longUrl = DnsMemoryDB.selectRecord(new StringBuffer(ConstUtil.SHORT_LONG_KEY_PREFIX ).append(url.substring(url.lastIndexOf("/") + 1)).toString());
        if (!StringUtils.hasLength(longUrl)){
            log.warn("对应url {},没有找到原链接", url);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.write("{\"result\":\"error\",\"msg\":\"抱歉，原链接已过期销毁\"}");
            out.flush();
            return;
        }

        response.setStatus(302);
        response.setHeader("Location", longUrl);

    }
}
