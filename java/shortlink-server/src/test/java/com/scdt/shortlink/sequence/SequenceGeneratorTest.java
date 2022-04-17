package com.scdt.shortlink.sequence;

import com.scdt.shortlink.util.ConversionUtil;
import com.scdt.shortlink.util.sequence.SequenceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xbhong
 * @date 2022/4/16 20:19
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
            String code = ConversionUtil.X.encode62(generate);
            System.out.println(code);
        } catch (Exception e) {
            log.error("sequenceGenerator failed:"+e);
        }
    }

}
