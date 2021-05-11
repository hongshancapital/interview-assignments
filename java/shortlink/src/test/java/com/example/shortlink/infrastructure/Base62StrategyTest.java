package com.example.shortlink.infrastructure;

import com.example.shortlink.infrastructure.codec.Base62Strategy;
import com.example.shortlink.infrastructure.codec.CodecStrategy;
import com.example.shortlink.infrastructure.common.IdGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Base62StrategyTest {
    CodecStrategy codecStrategy = new Base62Strategy();
    @Test
    public void base62Encode(){
        String encode= codecStrategy.encode(IdGenerator.getUniqueId("http://www.baidu.com"));
        assertTrue(encode!=null&&!encode.isEmpty());
    }
    @Test
    public void base62EncodeWithZero(){
        String encode= codecStrategy.encode(0);
        System.out.println(encode);
        assertTrue(encode.equals("1"));
    }
    @Test
    public void base62EncodeWithLessZero(){
        String encode= codecStrategy.encode(-1);
        System.out.println(encode);
        assertTrue(encode!=null&&!encode.isEmpty());
    }


    @Test
    public void base62Decode(){
        String encode= "0SABDg";
        long decode= codecStrategy.decode(encode);
        assertTrue(decode!=0);
    }

    @Test
    public void base62DecodeWithNull(){
        long decode= codecStrategy.decode(null);
        assertTrue(decode==0);
    }
    @Test
    public void base62DecodeWithEmpty(){
        long decode= codecStrategy.decode("");
        assertTrue(decode==0);
    }
    @Test
    public void base62DecodeWithABC(){
        long decode= codecStrategy.decode("abc");
        System.out.println(decode);
        assertTrue(decode!=0);
    }

}
