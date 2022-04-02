package com.getao.urlconverter.dto.generator;

import com.getao.urlconverter.util.ConverterUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class IdGeneratorTest {

    private final int generaterId = 0;

    private final int curNum = 0;

    private final int size = 1000;

    @Test
    public void testIdGenerator() {

        IdGenerator generator = new IdGenerator(generaterId, curNum, size);
        for(int i=0; i < size; i++) {
            long num = generator.getCurNum();
            System.out.println(num);
            assertThat(num).isNotNull();
        }
    }
}
