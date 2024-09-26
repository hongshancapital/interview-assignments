package org.example.dao;

import com.alibaba.fastjson.JSONObject;
import org.example.model.SearchDataResult;
import org.junit.Test;

import java.util.*;

public class LongUrl2ShortEsDAOTest {


    private LongUrl2ShortEsDAO longUrl2ShortEsDAO = new LongUrl2ShortEsDAO();

    @Test
    public void cleanTest() throws Exception {
        this.longUrl2ShortEsDAO.cleanIndex();
    }

    @Test
    public void searchTest(){
        try {
            Map<String, Object> conditionMap = new HashMap<>();
            conditionMap.put("long_url", "www.sohhhhhhhh.ddd.eee.aaa10.com");
            SearchDataResult searchDataResult = this.longUrl2ShortEsDAO.search(conditionMap, 0, 10);
            System.out.println(searchDataResult.getDatas());
            System.out.println(searchDataResult.getCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest(){
        try {
            this.longUrl2ShortEsDAO.delete("051d196a-ca54-4946-93af-71c65cbd4539");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pitchAddTest(){
        List<Map<String, Object>> dataMapList = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("id", UUID.randomUUID().toString());
        dataMap.put("long_url", "1");
        dataMap.put("short_url", "1");
        dataMap.put("create_time", System.currentTimeMillis());


        dataMapList.add(dataMap);
        try {
            this.longUrl2ShortEsDAO.pitchAdd(dataMapList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}