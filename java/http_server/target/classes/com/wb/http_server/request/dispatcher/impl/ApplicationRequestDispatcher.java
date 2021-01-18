package com.wb.http_server.request.dispatcher.impl;

import com.wb.http_server.exception.ResourceNotFoundException;
import com.wb.http_server.exception.base.ServletException;
import com.wb.http_server.request.Request;
import com.wb.http_server.request.dispatcher.RequestDispatcher;
import com.wb.http_server.resource.ResourceHandler;
import com.wb.http_server.response.Response;
import com.wb.http_server.util.IOUtil;
import com.wb.http_server.util.MimeTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author bing.wang
 * @date 2021/1/15
 * 请求转发器
 */

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ApplicationRequestDispatcher implements RequestDispatcher {
    private String url;
    
    @Override
    public void forward(Request request, Response response) throws ServletException, IOException {
        if (ResourceHandler.class.getResource(url) == null) {
            throw new ResourceNotFoundException();
        }

        log.info("forward至 {}",url);
        response.setContentType(MimeTypeUtil.getTypes(url));
        response.setBody(IOUtil.getBytesFromFile(url));
    }
}
