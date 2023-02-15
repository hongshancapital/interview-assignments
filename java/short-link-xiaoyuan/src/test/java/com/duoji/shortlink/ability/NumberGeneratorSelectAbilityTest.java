package com.duoji.shortlink.ability;

import com.duoji.shortlink.ability.generator.NumberGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 21:24:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NumberGeneratorSelectAbilityTest {

    @Resource
    private NumberGeneratorSelectAbility numberGeneratorSelectAbility;

    @Resource
    private NumberGeneratorAbility numberGeneratorAbility;

    @Test
    public void testSelect(){

        assertNull(numberGeneratorSelectAbility.selectOneRandom(null));
        assertNull(numberGeneratorSelectAbility.selectOneWeight(null));

        assertNull(numberGeneratorSelectAbility.selectOneRandom(new ArrayList<>()));
        assertNull(numberGeneratorSelectAbility.selectOneWeight(new ArrayList<>()));


        List<NumberGenerator> numberGeneratorList = numberGeneratorAbility.createAutoincrementNumberGenerator(10L,10L,100L);

        assertNotNull(numberGeneratorSelectAbility.selectOneRandom(numberGeneratorList));
        numberGeneratorSelectAbility.removeOneNumberGenerator(numberGeneratorList,numberGeneratorList.get(0));
        assertEquals(9,numberGeneratorList.size());
        assertNotNull(numberGeneratorSelectAbility.selectOneWeight(numberGeneratorList));

    }

}
