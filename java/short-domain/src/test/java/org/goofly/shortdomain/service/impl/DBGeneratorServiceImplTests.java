package org.goofly.shortdomain.service.impl;

import org.goofly.shortdomain.service.GeneratorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class DBGeneratorServiceImplTests {

    @Test
    public void testDBGenerate() {
        GeneratorService dbGeneratorService = new DBGeneratorServiceImpl();
        Assert.assertNotNull(dbGeneratorService);
        Assert.assertThrows(RuntimeException.class, () -> dbGeneratorService.generateCode());
    }
}
