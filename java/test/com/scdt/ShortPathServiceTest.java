package com.scdt;

import com.scdt.service.ShortPathService;
import com.scdt.util.Tools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShortPathServiceTest {
    @Autowired
    ShortPathService shortPathService;

    @Test
    public void testSortPathGeneration() {
        String shortpath = Tools.getRandomString(8);
        Assertions.assertEquals(8, shortpath.length());
    }

    @Test
    public void testShortPathService() {
        String shortPath = shortPathService.getShortPath("give_some_info");
        String longPath = shortPathService.getLongPath(shortPath);
        Assertions.assertEquals("give_some_info", longPath);
    }

    @Test
    public void testShortPathServiceNonExistence() {
        String shortPath = "yhskAk12";
        String longPath = shortPathService.getLongPath(shortPath);
        Assertions.assertEquals("", longPath);
    }
}
