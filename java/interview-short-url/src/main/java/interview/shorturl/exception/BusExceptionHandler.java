package interview.shorturl.exception;

import com.alibaba.fastjson.JSON;
import interview.shorturl.response.ResponseCodeEnum;
import interview.shorturl.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author vanki
 */
@ControllerAdvice
public class BusExceptionHandler {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public void exception(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseData responseData;
        try {
            if (ex instanceof BusException) {
                final BusException exObj = ((BusException) ex);
                responseData = new ResponseData(exObj.getCode(), exObj.getMsg(), exObj.getData());
                LOG.warn(String.format("url: %s, res: %s", request.getRequestURI(), responseData));
            } else if (ex instanceof IllegalArgumentException) {
                responseData = new ResponseData(ResponseCodeEnum.PARAM_ERROR, "必填字段不能为空");
                LOG.warn("必填字段不能为空", ex);
            } else if (ex instanceof NullPointerException && ex.getMessage() != null && ex.getMessage().contains("Parameter")) {
                responseData = new ResponseData(ResponseCodeEnum.PARAM_ERROR, "必填字段不能为空");
                LOG.warn("必填字段不能为空", ex);
            } else if (ex instanceof IllegalStateException) {
                responseData = new ResponseData(ResponseCodeEnum.PARAM_ERROR, "参数类型转换错误");
                LOG.warn("参数类型转换错误", ex);
            } else if (ex instanceof HttpRequestMethodNotSupportedException) {
                responseData = new ResponseData(ResponseCodeEnum.HTTP_METHOD_NOT_SUPPORTED);
                LOG.warn("不支持的请求方法", ex);
            } else {
                responseData = new ResponseData(ResponseCodeEnum.FAIL);
                LOG.warn("其他异常", ex);
            }
        } catch (Exception e) {
            responseData = new ResponseData(ResponseCodeEnum.FAIL);
            LOG.error("", e);
            LOG.error("原异常", ex);
        }
        response.getWriter().println(JSON.toJSONString(responseData));
        response.getWriter().flush();
    }
}
