import com.interview.InterviewApp;
import com.interview.util.Number62ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: nyacc
 * @Date: 2021/12/17 15:24
 */

@Slf4j
@SpringBootTest(classes = {InterviewApp.class})
@ActiveProfiles(value = "dev")
@RunWith(SpringRunner.class)
public class UtilsTest {

    @Test
    public void testUtilMethod(){
        long num= (long) (Math.pow(64, 4)-123);
        String encode = Number62ConvertUtil.encode(num);
        String expect="BIY4R";
        Assert.assertEquals(encode,expect);
    }
}
