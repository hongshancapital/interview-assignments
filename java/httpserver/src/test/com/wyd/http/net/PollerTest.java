package com.wyd.http.net;

import com.wyd.http.server.BootstrapTest;
import com.wyd.http.server.HttpServerConfig;
import com.wyd.http.server.net.Poller;
import com.wyd.http.server.net.Request;
import com.wyd.http.server.net.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public class PollerTest extends BootstrapTest {

    private static final String request_str =
            "GET /pom.xml HTTP/1.1\r\n" +
                    "Host: localhost:3555\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Cache-Control: max-age=0\r\n" +
                    "sec-ch-ua: \"Google Chrome\";v=\"87\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"87\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "Upgrade-Insecure-Requests: 1\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Sec-Fetch-Site: none\r\n" +
                    "Sec-Fetch-Mode: navigate\r\n" +
                    "Sec-Fetch-User: ?1\r\n" +
                    "Sec-Fetch-Dest: document\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "Accept-Language: zh-CN,zh;q=0.9\r\n";

    @Before
    public void testbefore() {
        testLoadConfWith();
    }

    //http://localhost:3555/http%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%AE%80%E5%8D%95%E6%80%9D%E8%B7%AF1.png

    /**
     * html ,png等时间关系省略
     */
    @Test
    public void parseRequestWithXML() {
        Poller poller = new Poller();
        HttpServerConfig instance = HttpServerConfig.Sinalton.SINALTON.getInstance();
        Request request = new Request();
        ByteBuffer wrap = ByteBuffer.wrap(request_str.getBytes());
        Response response = poller.processRequest(request, wrap);
        byte[] bytes = request.getHeader().get("Cache-Control");
        Assert.assertEquals(new String(bytes).trim(), "max-age=0");
        //
        String username = System.getProperty("user.name");
        String alias = instance.getAlias();
        if (!username.equals("wangyadong")) {
            alias = System.getProperty("user.dir");
        }
        File file = new File(alias + request.getPath());
        Assert.assertTrue(file.exists());
        Assert.assertTrue(response.getGetStatus().equals("200"));
    }

}
