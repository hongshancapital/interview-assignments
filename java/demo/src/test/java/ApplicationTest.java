import com.demo.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by gouyunfei on 2021/4/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@ActiveProfiles("")
public class ApplicationTest {
    @Test
    public void contextLoads(){
    }
}
