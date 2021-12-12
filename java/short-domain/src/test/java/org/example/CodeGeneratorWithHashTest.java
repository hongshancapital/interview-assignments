package org.example;

import org.example.exception.CodeGeneratorException;
import org.example.model.CodeGeneratorWithHash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentSkipListSet;

@SpringBootTest
public class CodeGeneratorWithHashTest {

    @Autowired
    private CodeGeneratorWithHash codeGeneratorWithHash;

    @BeforeEach
    public void beforeEach() throws Exception {
        Field usedNumberField = codeGeneratorWithHash.getClass().getDeclaredField("usedNumber");
        usedNumberField.setAccessible(true);
        usedNumberField.set(null, new ConcurrentSkipListSet<>());
    }

    @Test
    public void createNewCode_test() {
        String newCode = codeGeneratorWithHash.createNewCode("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E7%BA%A2%E8%A1%AB%E4%B8%AD%E5%9B%BD&fenlei=256&oq=%25E7%25BA%25A2%25E8%25A1%25AB%25E4%25B8%25AD%25E5%259B%25BD%25E5%2592%258C%25E7%25BA%25A2%25E6%259D%2589%25E7%259A%2584%25E5%2585%25B3%25E7%25B3%25BB&rsv_pq=87fbad3a0016c3b9&rsv_t=a27dqUeqGr0f6n3tF4gSa8mhYGrQ44xD6NVUTDf6tOYwj6hpZZno0U5b4AM&rqlang=cn&rsv_dl=tb&rsv_enter=1&rsv_btype=t&inputT=225&rsv_sug3=33&rsv_sug1=38&rsv_sug7=100&rsv_sug2=0&rsv_sug4=858&rsv_sug=1");
        Assertions.assertTrue(StringUtils.isNotBlank(newCode));

        String c1 = codeGeneratorWithHash.createNewCode("http://www.baidu.com");
        Assertions.assertTrue(StringUtils.isNotBlank(newCode));

        String c2 = codeGeneratorWithHash.createNewCode("http://www.baidu.com");
        Assertions.assertNotEquals(c1, c2);
    }
}
