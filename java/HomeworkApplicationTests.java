package com.example.homework;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
//import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class HomeworkApplicationTests {

//	@Test
//	void contextLoads() {
//		System.out.println("========testetste");
//	}

	@Resource
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    //@Before
    @PostConstruct
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
