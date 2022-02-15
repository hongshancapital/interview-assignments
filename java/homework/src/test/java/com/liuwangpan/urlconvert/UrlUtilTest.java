package com.liuwangpan.urlconvert;

import com.liuwangpan.urlconvert.utils.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Deacription
 * @author wp_li
 **/
@Slf4j
@SpringBootTest
public class UrlUtilTest {

    @Test
    public void isValidationUrlTest() throws Exception {
        boolean result = UrlUtil.isValidationUrl("");
        Assert.assertEquals(false, result);

    }
}