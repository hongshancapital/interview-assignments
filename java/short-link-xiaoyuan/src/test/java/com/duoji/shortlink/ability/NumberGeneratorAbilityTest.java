package com.duoji.shortlink.ability;

import com.duoji.shortlink.ability.generator.NumberGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 20:42:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NumberGeneratorAbilityTest {
    @Resource
    private NumberGeneratorAbility numberGeneratorAbility;

    @Test
    public void testCreateAutoincrementNumberGenerator() {
        List<NumberGenerator> numberGeneratorList = numberGeneratorAbility.createAutoincrementNumberGenerator(10L,10L,100L);
        assertEquals(10,numberGeneratorList.size());
        assertNotNull(numberGeneratorList.get(0).generateCode());
        for(int i=0;i<10;i++){
            numberGeneratorList.get(0).generateCode();
        }
        assertNull(numberGeneratorList.get(0).generateCode());
    }

    @Test
    public void testCreateCacheQueueNumberGenerator(){
        List<NumberGenerator> numberGeneratorList = numberGeneratorAbility.createCacheQueueNumberGenerator(10L,10L,100L);
        assertEquals(10,numberGeneratorList.size());
        assertNotNull(numberGeneratorList.get(0).generateCode());
        for(int i=0; i<10; i++){
            numberGeneratorList.get(0).generateCode();
        }
        assertNull(numberGeneratorList.get(0).generateCode());
    }

}
