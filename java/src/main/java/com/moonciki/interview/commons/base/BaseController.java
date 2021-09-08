package com.moonciki.interview.commons.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@Slf4j
public class BaseController {

    /**
     * 返回附件格式
     */
    public void responseAttachment(HttpServletResponse response, byte[] fileBytes, String fileName) throws Exception{
        OutputStream output = null;
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream");

            output = response.getOutputStream();
            output.write(fileBytes);

        } catch (IOException e) {
            log.error("Download File ERROR:", e);
            e.printStackTrace();
        }finally {
            if(output != null){
                output.flush();
                output.close();
            }
        }
    }

    /**
     * 直接返回流
     */
    public void responseBytes(HttpServletResponse response, byte[] fileBytes) throws Exception{
        OutputStream output = null;
        try {
            output = response.getOutputStream();
            output.write(fileBytes);
        } catch (IOException e) {
            log.error("Download File ERROR:", e);
            responseNotfound(response);
        }finally {

            output.close();
        }
    }

    /**
     * 未找到资源
     * @param response
     * @throws Exception
     */
    public void responseNotfound(HttpServletResponse response) throws Exception{
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

}
