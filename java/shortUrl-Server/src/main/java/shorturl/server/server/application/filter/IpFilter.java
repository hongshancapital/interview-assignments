package shorturl.server.server.application.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shorturl.server.server.application.exception.ErrorResponse;
import shorturl.server.server.application.util.IPUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@Slf4j
@Component
public class IpFilter implements Filter {

    @Value("${black.ip.list}")
    private Set<String> blackIpList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String ip = IPUtil.getRemoteIpAddr(req);

        if(blackIpList.contains(ip)){
            log.info("{} 命中ip黑名单", ip);
            PrintWriter writer = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            writer.write(ErrorResponse.notFound(""));
            writer.flush();
            writer.close();
            return ;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
// TODO Auto-generated method stub

    }

}
