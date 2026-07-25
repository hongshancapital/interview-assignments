package org.zxl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zxl.advise.CommonException;
import org.zxl.advise.CommonResponse;
import org.zxl.domain.Segment;
import org.zxl.interceptor.PermissionInterceptor;
import org.zxl.manager.DomainManager;
import org.zxl.service.DomainService;

/**
 * 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UnitTest {

    @Autowired
    private DomainService domainService;
    @Autowired
    private DomainManager domainManager;
    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Test(expected = Exception.class)
    public void testDomainService() {
        System.out.println(domainService.isValidUrl("http://ss"));
        domainService.convertToLong("a00002");
    }

    @Test
    public void testPermissionInterceptor() {
        permissionInterceptor.preHandle(null, null, new Object());
        permissionInterceptor.postHandle(null, null, null, null);
    }

    @Test
    public void testDomainManager() {
        domainManager.check();
        domainManager.convertToLongUrl("test");
    }

    @Test
    public void testSegment() {
        Segment segment = new Segment("a00001", "a00010");
        segment.setStart("1");
        segment.setEnd("2");
        System.out.println(segment.getEnd());
    }

    @Test
    public void testResponse() {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMessage("");
        commonResponse.setStatus(0);
        commonResponse.setCode(0);
        commonResponse.setPath("");
        commonResponse.setEntity("");

        commonResponse.getMessage();
        commonResponse.getStatus();
        commonResponse.getCode();
        commonResponse.getPath();
        commonResponse.getEntity();

        CommonException commonException =new CommonException();
        commonException.setCode(1);
        commonException.setMessage("");
        commonException.setStatusCode(1);
        commonException.setErrors(null);

        commonException.getCode();
        commonException.getMessage();
        commonException.getStatusCode();
        commonException.getErrors();
    }
}
