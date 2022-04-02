package com.getao.urlconverter.dto.generator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NumGeneratorPoolTest {

    @Test
    public void GeneratorPoolTest() {
        NumGeneratorPool pool = new NumGeneratorPool();
        for(int i=0; i<100; i++) {
            assertThat(pool.getEncodedUrl()).isNotEmpty();
            System.out.println(pool.getEncodedUrl());
        }
    }
}
