package com.hongshan.work;
import com.hongshan.work.util.ConversionUtils;
import com.hongshan.work.util.SequenceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @author yuanyang
 * @date 2021/12/22 7:40 下午
 * @Describe
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SequenceGeneratorTest {
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Test
    public void createShortCode() {
        try {
            long generate = sequenceGenerator.generate();
            String code = ConversionUtils.X.encode62(generate);
            code  = code.substring(code.length() - 6);
            System.out.println(code);
        } catch (Exception e) {
            log.error("sequenceGenerator createShortCode failed:"+e);
        }
    }

} 