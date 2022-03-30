package com.example.shortlink;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import org.apache.catalina.core.ApplicationContext;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
public abstract class BaseTest {

    @Autowired
    protected Gson gson;

    // object to be assigned
    protected Object result;


    /**
     * print result to json
     *
     */
    @After
    public void printResult2Json() {
        log.info(gson.toJson(result));
    }
}
