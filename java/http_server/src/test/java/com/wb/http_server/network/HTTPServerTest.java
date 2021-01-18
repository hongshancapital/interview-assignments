package com.wb.http_server.network;

import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.exception.base.ServletException;
import com.wb.http_server.request.Request;
import com.wb.http_server.response.Response;
import com.wb.http_server.servlet.impl.DefaultServlet;
import com.wb.http_server.session.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
@Slf4j
public class HTTPServerTest {

    private HTTPServer httpServer;

    @Before
    public void setHttpServer(){
        httpServer = new HTTPServer();
        httpServer.start(83);
    }

    @Test
    public void testNotLogin(){
        URL url;
        try {
            url = new URL("http://localhost:83/file/pom.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int rc = connection.getResponseCode();
            Assert.assertEquals(HttpStatus.NOT_AUTH.getCode(),rc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLogin(){
        URL url;
        try {
            url = new URL("http://localhost:83/http_server/file/pom.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization","Basic dGVzdDp0ZXN0");
            connection.connect();
            int rc = connection.getResponseCode();
            Assert.assertEquals(HttpStatus.OK.getCode(),rc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
