package com.wb.http_server.response;

import com.wb.http_server.context.WebApplication;
import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.network.HTTPServer;
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
 * @description
 */
public class ResponseTest {
    private HTTPServer httpServer;
    @Before
    public void setHttpServer(){
        httpServer = new HTTPServer();
        httpServer.start(83);
    }
    @Test
    public void testResponse(){
        WebApplication.getServletContext().addServlet("res","/res/*","com.wb.http_server.response.ResponseServlet");
        URL url;
        try {
            url = new URL("http://localhost:83/res/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization","Basic dGVzdDp0ZXN0");
            connection.connect();
            Assert.assertEquals(connection.getResponseCode(),HttpStatus.INTERNAL_SERVER_ERROR.getCode());
            Assert.assertEquals(connection.getHeaderField("testHeader"),"test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
