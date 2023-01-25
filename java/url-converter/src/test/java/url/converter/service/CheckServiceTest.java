package url.converter.service;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import url.converter.service.impl.CheckServiceImpl;

/**
 * @author Wang Siqi
 * @date 2022/1/1
 */
@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class CheckServiceTest {

    @InjectMocks
    private CheckServiceImpl checkService;

    @Test
    public void testCheckLong() {
        try {
            checkService.checkLongUrl("aaa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckLong2() {
        try {
            checkService.checkLongUrl("https://www.xx.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckShort() {
        try {
            checkService.checkShortUrl("aaa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckShort2() {
        try {
            checkService.checkShortUrl("https://www.xx.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
