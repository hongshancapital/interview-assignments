package com.task.shortlinkandlonglink;

import com.task.utils.inter.MyMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test") //使用单元测试配置
public class MyHashMapTest {

    @Autowired
    private MyMap<Integer,Integer> myHashMap;

    /**
     * 测试 MyHashMap
     */
    @Test
    public void testMyHashMap(){
        int i;
        for (i=0;i<1000;i++){
            myHashMap.put(i,i);
            if (myHashMap.containsKey(i)){
                System.out.println(myHashMap.get(i));
            }
        }
        Set<Integer> integers = myHashMap.keySet();
        System.out.println(integers);

    }

}
