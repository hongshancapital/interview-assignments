package flypig.url.mapping;

import flypig.url.mapping.core.UrlMapperContext;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataInitial {

    @Before
    public void setUp() {
        UrlMapperContext urlMapper = UrlMapperContext.getInstance();
        BufferedReader testUrls = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(
                "/test_url.txt")));
        String url = null;
        try {
            while ((url = testUrls.readLine()) != null) {
                urlMapper.long2short(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
