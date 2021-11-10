package homework.shorturl.service;

import homework.shorturl.model.dto.UrlTransDTO;
import homework.shorturl.service.impl.UrlTransServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlTransServiceTest {
    @Resource
    private UrlTransServiceImpl urlTransService;

    @Test
    public void trans() {
        //输入32条不重复长链
        Map<String, String> paramMap = new HashMap<>();
        String urlPrefix = "xxx.xxx.xxx/xxx/xxx/xxx";
        UrlTransDTO dto = new UrlTransDTO();
        for (int i = 0; i < 32; i++) {
            String url =  urlPrefix + i;
            dto.setUrl(url);
            UrlTransDTO transShort = null;
            try {
                transShort = urlTransService.transShort(dto);
                System.out.println(transShort.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            paramMap.put(url, transShort.getShortUrl());
        }

        //输入已有长链
        dto.setUrl("xxx.xxx.xxx/xxx/xxx/xxx30");
        try {
            UrlTransDTO transShort = urlTransService.transShort(dto);
            System.out.println(transShort.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //查询已有短链，其中包含被过期的
        for (String shortUrl : paramMap.values()) {
            try {
                dto.setShortUrl(shortUrl);
                UrlTransDTO transLong = urlTransService.transLong(dto);
                System.out.println(transLong.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
