package com.wb.http_server.context;

import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.exception.ServletNotFoundException;
import com.wb.http_server.network.HTTPServer;
import com.wb.http_server.servlet.Servlet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author bing.wang
 * @date 2021/1/15
 * @description
 */
public class ContextServletTest {
    private HTTPServer httpServer;
    @Before
    public void setHttpServer(){
        httpServer = new HTTPServer();
        httpServer.start(83);
    }

    @Test
    public void testRouteMap() throws  ServletNotFoundException {

        WebApplication.getServletContext().addServlet("access","/access/*","com.wb.http_server.context.AccessContextServlet");
        Servlet servlet = WebApplication.getServletContext().mapServlet("/access/");
        Assert.assertEquals(servlet.getClass(),AccessContextServlet.class);

    }

    @Test
    public void testSession1(){
        WebApplication.getServletContext().addServlet("access","/access/*","com.wb.http_server.context.AccessContextServlet");
        URL url;
        try {
            url = new URL("http://localhost:83/access/");
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
