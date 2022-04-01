package com.getao.urlconverter.numGenerator;

import com.getao.urlconverter.dto.generator.NumGeneratorPool;
import org.junit.jupiter.api.Test;

public class numGeneratorTest {

    @Test
    public void testNumGenerator() {
        NumGeneratorPool pool = new NumGeneratorPool();
        for(int i=0; i<100; i++) {
            System.out.println(pool.getEncodedUrl());
        }
    }
}
