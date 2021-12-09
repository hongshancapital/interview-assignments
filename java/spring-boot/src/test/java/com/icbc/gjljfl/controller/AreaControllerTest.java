package com.icbc.gjljfl.controller;

import com.icbc.gjljfl.area.controller.AreaController;
import com.icbc.gjljfl.common.ResponseEntity;
import com.icbc.gjljfl.common.enums.ErrorEnum;
import com.icbc.gjljfl.util.ResponseUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AreaControllerTest {
    @Resource
    AreaController areaController ;

    @Test
    public void saveUrl() {
        String url_short = "www.baidu.com";
        assertNull(areaController.saveUrl(""));
        assertNull(areaController.saveUrl(null));
        assertNotNull(areaController.saveUrl(url_short));

    }

    @Test
    public void readUrl() {
        String url_long = "longUrl1";
        assertNull(areaController.readUrl(""));
        assertNull(areaController.readUrl(null));
        assertNotNull(areaController.readUrl(url_long));

    }

}
