package com.youming.sequoia.sdn.apipublic.manager;

import com.youming.sequoia.sdn.apipublic.config.SpringApplicationConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringApplicationConfig.class)
public class TestIdGeneratorManager {


    @Autowired
    private IdGeneratorManager idGeneratorManager;

    @Test
    public void testGetId() {
        long id = idGeneratorManager.getId();

    }

    
}
