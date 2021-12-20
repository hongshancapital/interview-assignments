package com.duoji.shortlink.ability;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 19:14:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileOperateAbilityTest {
    @Resource
    private FileOperateAbility fileOperateAbility;

    @Test
    public void testReadFile(){
        assertEquals("",fileOperateAbility.readFile("12"));
        assertEquals("8",fileOperateAbility.readFile("machineId"));
    }

    @Test
    public void testWriteFile(){
        fileOperateAbility.writeFile("test.txt","test");
        assertEquals("test",fileOperateAbility.readFile("info/test.txt"));
    }
}
