import com.github.kevinsawicki.http.HttpRequest;
import com.luman.http.Response;
import com.luman.http.server.HttpServer;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerTest {

    @Test
    public void testHtml(){
        ExecutorService es = Executors.newSingleThreadExecutor();
        HttpServer httpServer = new HttpServer();
        es.submit(httpServer::start);
        String indexHtml = HttpRequest.get("http://localhost:8080/index.html").body();
        TestCase.assertNotNull(indexHtml);
        TestCase.assertTrue(indexHtml.contains("this index html"));
        httpServer.stop();

    }


    @Test
    public void testJpg(){
        ExecutorService es = Executors.newSingleThreadExecutor();
        HttpServer httpServer = new HttpServer();
        es.submit(httpServer::start);
        try{
            BufferedInputStream inputStream = HttpRequest.get("http://localhost:8080/test.jpg").buffer();
            try {
                Path path = Files.createTempFile("jpg_", ".jpg");
                Files.write(path, inputStream.readAllBytes());
                String contentType = Files.probeContentType(path);
                TestCase.assertEquals(contentType, "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }finally {
            httpServer.stop();
        }
    }
    @Test
    public void testXml(){
        ExecutorService es = Executors.newSingleThreadExecutor();
        HttpServer httpServer = new HttpServer();
        es.submit(httpServer::start);
        try{
            BufferedInputStream inputStream = HttpRequest.get("http://localhost:8080/test.jpg").buffer();
            try {
                Path path = Files.createTempFile("xml_", ".xml");
                Files.write(path, inputStream.readAllBytes());
                String contentType = Files.probeContentType(path);
                TestCase.assertEquals(contentType, "application/xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }finally {
            httpServer.stop();
        }
    }
}
