package com.luman.shorturl.uid;

import com.luman.shorturl.common.util.Radix62;
import com.luman.shorturl.uid.UidGenerator;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UidTest{
    @Resource
    private UidGenerator uidGenerator;
    @Test
    public void uid(){
        long uid = uidGenerator.getUID();
        TestCase.assertTrue(uid>0);
        String code = Radix62.to62Radix(uid);
        TestCase.assertEquals(Radix62.toLong(code), uid);
    }
}