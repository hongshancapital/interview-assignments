import com.zhucan.domain.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 17:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment =SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class AppTests {

  @Test
  public void contextLoads() {

  }
}
