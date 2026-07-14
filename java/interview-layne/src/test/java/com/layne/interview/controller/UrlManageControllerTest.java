package com.layne.interview.controller;

import com.layne.interview.InterviewApplication;
import com.layne.interview.model.ErrorCodeEnum;
import com.layne.interview.service.UrlManageService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UrlManageControllerTest {

    @Autowired
    private UrlManageController urlManageController;

    /**
     * 以下是getUrl的测试用例
     */
    @Test
    public void test_get_success() {
        String longUrl = "https://github.com/csuWangLY/interview-assignments/tree/master/javatest_get_success";
        String shortUrl = urlManageController.store(longUrl).getObj();

        Assert.assertEquals(longUrl, urlManageController.get(shortUrl).getObj());
    }

    @Test
    public void test_get_not_exist_short_url() {
        String longUrl = "https://github.com/csuWangLY/interview-assignments/tree/master/javatest_get_not_exist_short_url";
        String shortUrl = urlManageController.store(longUrl).getObj();

        Assert.assertEquals(ErrorCodeEnum.REQUEST_DATA_NOT_EXIST.getCode(), urlManageController.get(shortUrl + " ").getErrorCode());
    }


    /**
     * 以下是存储的用例
     */
    @Test
    public void test_store_with_new_long_url_success() {
        // 保证此生成方法的幂等性
        String longUrl = "https://github.com/csuWangLY/interview-assignments/tree/master/javatest_store_with_new_long_url_success";
        Assert.assertEquals("dcgskg00", urlManageController.store(longUrl).getObj());
    }

    @Test
    public void test_store_with_long_url_shorter_than_8() {
        String longUrl = "b.com";
        Assert.assertEquals("RVlso400", urlManageController.store(longUrl).getObj());
    }

    @Test
    public void test_store_with_check_para_failed() {
        Assert.assertEquals(ErrorCodeEnum.BAD_REQUEST.getCode(), urlManageController.store("").getErrorCode());
    }


    @Test
    public void test_store_with_same_url() {
        String shortUrl = urlManageController.store("https://github.com/csuWangLY/interview-assignments/tree/master/javatest_store_with_same_url").getObj();

        Assert.assertEquals(shortUrl, urlManageController.store("https://github.com/csuWangLY/interview-assignments/tree/master/javatest_store_with_same_url").getObj());
    }


    @Test
    public void test_xstore_with_internal_error_exception() {
        // 在调用urlManageService时会返回一个runtimeException
        UrlManageService urlManageService = Mockito.mock(UrlManageService.class);
        Mockito.reset(urlManageService);
        Mockito.when(urlManageService.storeUrl(Mockito.anyString())).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(urlManageController, "urlManageService", urlManageService);
        Assert.assertEquals(ErrorCodeEnum.INTERNAL_ERROR.getCode(), urlManageController.store("a.com").getErrorCode());
        Mockito.reset(urlManageService);
    }

}