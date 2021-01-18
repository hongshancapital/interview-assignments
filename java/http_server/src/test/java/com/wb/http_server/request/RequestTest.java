package com.wb.http_server.request;

import com.wb.http_server.context.WebApplication;
import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.network.HTTPServer;
import com.wb.http_server.network.wrapper.NioSocketWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public class RequestTest {

    private HTTPServer httpServer;
    @Before
    public void setHttpServer(){
        httpServer = new HTTPServer();
        httpServer.start(83);
    }
    @Test
    public void testRequest(){
        WebApplication.getServletContext().addServlet("req","/req/*","com.wb.http_server.request.RequestServlet");
        URL url;
        try {
            url = new URL("http://localhost:83/req/?param=OK");
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
            Assert.assertEquals("OK",msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
