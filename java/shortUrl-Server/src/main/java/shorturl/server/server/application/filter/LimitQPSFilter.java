package shorturl.server.server.application.filter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shorturl.server.server.application.exception.ErrorResponse;
import shorturl.server.server.application.util.IPUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class LimitQPSFilter implements Filter {

    RateLimiter r = RateLimiter.create(10000);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(!r.tryAcquire(1)){
            log.info("too many request");
            PrintWriter writer = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            writer.write(ErrorResponse.serverError(""));
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
