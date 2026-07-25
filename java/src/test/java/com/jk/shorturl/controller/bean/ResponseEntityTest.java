package com.jk.shorturl.controller.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jiang Jikun
 * @Description
 */
class ResponseEntityTest {

    ResponseEntity responseEntity = new ResponseEntity();
    @Test
    void success() {
        System.out.println(ResponseEntity.success("执行成功"));

    }

    @Test
    void failure() {
        System.out.println(ResponseEntity.failure("执行失败"));
    }

    @Test
    void setSuccess() {
        responseEntity.setSuccess(true);

        responseEntity.setAny("data","这是单独的数据");

        responseEntity.setSuccess(null);
        System.out.println(responseEntity.toString());
    }

    @Test
    void setMessage() {
        responseEntity.setMessage("单独设置执行成功");
        System.out.println(responseEntity.toString());

        responseEntity.setMessage(null);
        responseEntity.setAny("data",null);
        System.out.println(responseEntity.toString());
    }

    @Test
    void setAny() {
        responseEntity.setAny("data","单独的数据");
        System.out.println(responseEntity.toString());


        responseEntity.setAny("data",null);
        System.out.println(responseEntity.toString());


        responseEntity.setAny(null,null);
        System.out.println(responseEntity.toString());
    }
}
