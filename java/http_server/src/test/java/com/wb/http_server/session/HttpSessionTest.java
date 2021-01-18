package com.wb.http_server.session;

import com.wb.http_server.context.WebApplication;
import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.exception.base.ServletException;
import com.wb.http_server.network.HTTPServer;
import com.wb.http_server.request.Request;
import com.wb.http_server.response.Response;
import com.wb.http_server.servlet.impl.DefaultServlet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public class HttpSessionTest {

    private HTTPServer httpServer;
    @Before
    public void setHttpServer(){
        httpServer = new HTTPServer();
        httpServer.start(83);
    }
    @Test
    public void testSession1(){
        WebApplication.getServletContext().addServlet("test","/test/*","com.wb.http_server.session.TestSessionServlet");
        URL url;
        try {
            url = new URL("http://localhost:83/test/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization","Basic dGVzdDp0ZXN0");
            connection.connect();
            String msg = "";
            if (connection.getResponseCode() == HttpStatus.OK.getCode()) { // 正常响应
                // 从流中读取响应信息
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    msg += line;
                }
                reader.close(); // 关闭流
            }
            Assert.assertEquals("FAIL",msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

