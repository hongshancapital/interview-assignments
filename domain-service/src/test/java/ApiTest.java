import com.interview.InterviewApp;
import com.interview.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.krb5.internal.crypto.Aes128;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Description :
 * @Author: nyacc
 * @Date: 2021/12/17 15:44
 */

@Slf4j
@SpringBootTest(classes = {InterviewApp.class},webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles(value = "dev")
@RunWith(SpringRunner.class)
public class ApiTest {

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void test() throws UnsupportedEncodingException {
        String errorUrl= "http://localhost:8081/domain/v1/shortUrl?longUrl=    ";
        Response<String> errUrlResult = testRestTemplate.postForObject(errorUrl,null, Response.class);
        Assert.assertNull(errUrlResult.getData());

        String original="http://www.laiyifen.com/12?a=1&b=1";
        String longUrl= URLEncoder.encode(original, "utf-8");
        String targetUrl="http://localhost:8081/domain/v1/shortUrl?longUrl="+longUrl;

        Response<String> shortUrlResult = testRestTemplate.postForObject(targetUrl,null, Response.class);
        String shortUrl=shortUrlResult.getData();
        Assert.assertNotNull(shortUrl);
        log.info("长域名转短域名结果： "+shortUrlResult);
        String targetUrlV2 = "http://localhost:8081/domain/v1/longUrl?shortUrl="+shortUrl;

        Response<String> shortUrlResultV2 = testRestTemplate.postForObject(targetUrl,null, Response.class);
        String shortUrlV2=shortUrlResultV2.getData();
        Assert.assertEquals(shortUrl, shortUrlV2);
        log.info("同样长域名转短域名结果： "+shortUrlV2);
        Response<String> longUrlResponse = testRestTemplate.postForObject(targetUrlV2,null,Response.class);
        String longUrlStr = longUrlResponse.getData();
        Assert.assertNotNull(longUrlStr);
        longUrlStr= URLDecoder.decode(longUrlStr,"utf-8");
        log.info("短域获取长域结果:{}", longUrlStr);
        Assert.assertEquals(longUrlStr, original);
    }

}
