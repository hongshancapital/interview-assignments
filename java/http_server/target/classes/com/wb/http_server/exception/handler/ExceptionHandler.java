package com.wb.http_server.exception.handler;

import com.wb.http_server.exception.RequestInvalidException;
import com.wb.http_server.exception.base.ServletException;
import com.wb.http_server.network.wrapper.SocketWrapper;
import com.wb.http_server.response.Header;
import com.wb.http_server.response.Response;
import com.wb.http_server.util.IOUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.wb.http_server.constant.ContextConstant.ERROR_PAGE;

/**
 * @author bing.wang
 * @date 2021/1/15
 * 会根据异常对应的HTTP Status设置response的状态以及相应的错误页面
 */

@Slf4j
public class ExceptionHandler {

    public void handle(ServletException e, Response response, SocketWrapper socketWrapper) {
        try {
            if (e instanceof RequestInvalidException) {
                log.info("请求无法读取，丢弃");
                socketWrapper.close();
            } else {
                log.info("抛出异常:{}", e.getClass().getName());
                e.printStackTrace();
                response.addHeader(new Header("Connection", "close"));
                response.setStatus(e.getStatus());
                response.setBody(IOUtil.getBytesFromFile(
                        String.format(ERROR_PAGE, String.valueOf(e.getStatus().getCode()))));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
