package org.zxl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zxl.advise.CommonResponse;
import org.zxl.controller.DomainController;

/**
 * @author zhouxiliang
 * @date 2021/11/2 15:41
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DomainController.class)
public class DomainControllerUnitTest {

    @Autowired
    private DomainController domainController;

    @Test
    public void testDomainController() {
        for (int i = 0; i < 3; i++) {
            System.out.println(domainController.convertToShort("http://ss"));
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(domainController.convertToOriginUrl("a00002"));
        }
    }

    @Test(expected = Exception.class)
    public void testAdvise() {
        System.out.println(domainController.convertToShort("ss1"));
    }

    @Test
    public void testCommonResponse() {
        CommonResponse commonResponse = CommonResponse.ok("ok");
        commonResponse = CommonResponse.ok();
    }

}
