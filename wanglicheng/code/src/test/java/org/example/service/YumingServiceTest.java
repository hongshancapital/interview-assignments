package org.example.service;


import org.example.dao.LongUrl2ShortEsDAO;
import org.example.model.SearchDataResult;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YumingServiceTest {

    @Test
    public void short2LongTest(){
        LongUrl2ShortEsDAO longUrl2ShortEsDAO = new LongUrl2ShortEsDAO();

        try {
            longUrl2ShortEsDAO.cleanIndex();

            Map<String, Object> conditionMap = new HashMap<>();
            longUrl2ShortEsDAO.search(conditionMap, 0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        YumingService yumingService = new YumingService();
        try {
            String str1 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa1.com");
            String str2 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa2.com");
            String str3 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa3.com");
            String str4 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa4.com");
            String str5 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa5.com");
            String str6 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa6.com");
            String str7 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa7.com");
            String str8 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa8.com");
            String str9 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa9.com");
            String str10 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa10.com");
            String str11 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa11.com");
            String str12 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa12.com");
            String str13 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa13.com");
            String str14 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa14.com");
            String str15 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa15.com");
            String str16 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa16.com");
            String str17 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa17.com");
            String str18 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa18.com");
            String str19 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa19.com");
            String str20 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa20.com");
            String str21 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa21.com");
            String str22 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa22.com");
            String str23 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa23.com");
            String str24 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa24.com");
            String str25 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa25.com");
            String str26 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa26.com");
            String str27 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa27.com");
            String str28 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa28.com");
            String str29 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa29.com");
            String str30 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa30.com");
            String str31 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa31.com");
            String str32 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa32.com");
            String str33 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa33.com");
            String str34 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa34.com");
            String str35 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa35.com");
            String str36 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa36.com");
            String str37 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa37.com");
            String str38 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa38.com");
            String str39 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa39.com");
            String str40 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa40.com");
            String str41 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa41.com");
            String str42 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa42.com");
            String str43 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa43.com");
            String str44 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa44.com");
            String str45 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa45.com");
            String str46 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa46.com");
            String str47 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa47.com");
            String str48 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa48.com");
            String str49 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa49.com");
            String str50 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa50.com");
            String str51 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa51.com");
            String str52 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa52.com");
            String str53 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa53.com");
            String str54 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa54.com");
            String str55 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa55.com");
            String str56 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa56.com");
            String str57 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa57.com");
            String str58 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa58.com");
            String str59 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa59.com");
            String str60 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa60.com");
            String str61 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa61.com");
            String str62 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa62.com");
            String str63 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa63.com");
            String str64 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa64.com");
            String str65 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa65.com");
            String str66 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa66.com");
            String str67 = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa67.com");

            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa4.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa5.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa6.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa11.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa12.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa13.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa14.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa15.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa16.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa17.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa18.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa19.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa20.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa21.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa22.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa23.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa24.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa25.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa26.com");
            yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa27.com");

            String longStr = yumingService.long2Short("www.sohhhhhhhh.ddd.eee.aaa44.com");
            System.out.println(longStr);
            System.out.println("www.sohhhhhhhh.ddd.eee.aaa44.com");


            System.out.println(str67);

            String str = yumingService.short2Long(str67);
            System.out.println(str);

            String shortStr = yumingService.short2Long(str1);

            yumingService.short2Long("123bcief9");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //assertEquals("Please provide a name!", "Please provide a name!");
    }
}