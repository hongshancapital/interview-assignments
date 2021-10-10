package com.scdt.interview.url;

import com.scdt.interview.url.exception.GlobalExceptionHandler;
import com.scdt.interview.url.utils.R;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: lijin
 * @date: 2021年10月10日
 */
@SpringBootTest
public class ExceptionTests {

    @Autowired
    private GlobalExceptionHandler handler;

    @Test
    public void testR(){
        new R<>("", null,null);
        new R<>("", "",null);
        new R<>(null, "",null);
        new R<>(null, "","");

        R<String> r = new R<>("ok", "ok", "ok");
        Assertions.assertEquals("ok", r.getData());
        Assertions.assertEquals("ok",r.getMessage());
        Assertions.assertEquals("ok", r.getCode());


        r.setData(null);
        r.setMessage("fail");
        r.setCode("fail");

        Assertions.assertEquals("fail", r.getMessage());
        Assertions.assertNull(r.getData());
        Assertions.assertEquals("fail", r.getCode());


    }


}
