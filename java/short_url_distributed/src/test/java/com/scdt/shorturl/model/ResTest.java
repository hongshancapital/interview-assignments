package com.scdt.shorturl.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResTest {

    @Test
    public void constructTest(){
        Res<Record> res =  new Res<>(Res.ErrorCode.OK.getCode(),"test",new Record());
        Assertions.assertTrue(res.isSuccess());
    }
    @Test
    public void construct2Test(){
        Res res =  new Res(Res.ErrorCode.OK.getCode(), "test");
        Assertions.assertTrue(res.isSuccess());
        Res res2 =  new Res(Res.ErrorCode.FORBIDDEN.getCode(), "test");
        Assertions.assertFalse(res2.isSuccess());
    }

    @Test
    public void construct3Test(){
        Res res =  new Res();
        res.setCode(Res.ErrorCode.EXPIRED.getCode());
        res.setSuccess(false);
        res.setMsg("test");
        res.setData(null);
        Assertions.assertEquals("test",res.getMsg());
        Assertions.assertEquals(null,res.getData());
        Assertions.assertFalse(res.isSuccess());
    }

    @Test
    public void resErrorTest(){
        Res<Record> error = Res.error("error",new Record().setLongUrl("http://www.baidu.com").setShortUrl("iuh7ygtf"));
        Res<Record> success = Res.success(new Record().setLongUrl("http://www.baidu.com").setShortUrl("iuh7ygtf"));
        Res<Record> success2 = Res.success(new Record("iuh7ygtf","http://www.baidu.com"));
        Assertions.assertFalse(error.isSuccess());
        Assertions.assertTrue(success.isSuccess());
        Assertions.assertNotEquals(success2,success);
        Assertions.assertNotEquals(error,success);
        Assertions.assertNotEquals(error.toString(),success.toString());
    }
}
