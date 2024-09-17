package com.ccb.domain;

import com.ccb.domain.ctrl.ShortDomainCtrl;
import com.ccb.domain.generate.IDomainShorterGenerator;
import com.ccb.domain.generate.impl.ShorterStorageMemory;
import net.bytebuddy.asm.Advice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: nieyy
 * @Date: 2021/7/25 2:00
 * @Version 1.0
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class shortDomainCtrlTest{

    private Logger logger = LoggerFactory.getLogger(shortDomainCtrlTest.class);

    private MockMvc mockMvc;

    @Autowired
    private ShortDomainCtrl shortDomainCtrl;
    @Autowired
    private IDomainShorterGenerator horterString;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(shortDomainCtrl).build();
    }



    @Test
    public void getShortDomain() throws Exception {

        String longDomainName = horterString.generate(20);

        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getShortDomain")
                    .accept(MediaType.APPLICATION_JSON).param("longDomainName", longDomainName))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            String result = mvcResult.getResponse().getContentAsString();
            logger.info(result);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getLongDomainName() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getLongDomain")
                .accept(MediaType.APPLICATION_JSON).param("shortDomainName", "longDomainName" ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        logger.info(result);


    }




}
