package com.tb.link.qatest.infrastructure;

import com.google.common.hash.Hashing;
import com.tb.link.infrastructure.util.Base62String;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author andy.lhc
 * @date 2022/4/17 10:28
 */
@Slf4j
public class Base62StringTest {


    /**
     * 生成shortLink
     */
    @Test
    public void testGenerateShortLink(){
        long t= Hashing.murmur3_128().hashUnencodedChars("https://www.github.com/test?key=2&key3=5").asLong();
        String str = Base62String.generate(t) ;
        log.info(str);
        Assertions.assertEquals("Zt9Hpop",str);
    }

    /**
     * 随机生成
     */
    @Test
    public void testGenerateRandomShortLink(){
        long t= Hashing.murmur3_128().hashUnencodedChars("https://www.github.com/test?key=2&key3=5").asLong();
        String str = Base62String.generate(t,4) ;
        log.info(str);
        Assertions.assertNotNull(str);
    }

    /**
     * 生成8位长度
     */
    @Test
    public void testGenerateWith8Length(){
        long t= Hashing.murmur3_128().hashUnencodedChars("https://www.github.com/test?key=2&key3=5").asLong();
        String str = Base62String.generate8Length(t) ;
        Assertions.assertEquals(str.length(),8);
    }


}
