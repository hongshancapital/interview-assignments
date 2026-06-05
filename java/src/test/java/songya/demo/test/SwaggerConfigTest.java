package songya.demo.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import songya.demo.Starter;
import songya.demo.config.SwaggerConfig;
import songya.demo.util.ShortUrlGenerator;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Starter.class })
public class SwaggerConfigTest {

    @Autowired
    private SwaggerConfig swaggerConfig;

    @Test
    public void  docketTest(){
        Assert.assertNotNull(swaggerConfig.docket());
    }

}
