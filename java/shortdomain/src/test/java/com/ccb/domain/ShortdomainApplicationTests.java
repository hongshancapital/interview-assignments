package com.ccb.domain;

import com.ccb.domain.generate.IDomainShorterGenerator;
import com.ccb.domain.generate.impl.ShorterStorageMemory;
import com.ccb.domain.service.ShortDomainService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShortdomainApplicationTests {

    private Logger logger = LoggerFactory.getLogger(ShortdomainApplicationTests.class);

    @Autowired
    ShortDomainService shortDomainService;
    @Autowired
    ShorterStorageMemory shorterStorageMemory;
    @Autowired
    private IDomainShorterGenerator horterString;


    @Test
    void saveCache() throws InterruptedException {
        String longDomainName = horterString.generate(20);
        String shortDomainName = horterString.generate(8);
        shorterStorageMemory.save(shortDomainName,longDomainName,-1);
        String newLongDomainName = shorterStorageMemory.getLongDomain(shortDomainName);
        String newShortDomainName = shorterStorageMemory.getShortDomain(longDomainName);
        logger.info("newLongDomainName:" + newLongDomainName);
        logger.info("newShortDomainName:" + newShortDomainName);



        String longDomainName2 = horterString.generate(20);
        String shortDomainName2 = horterString.generate(8);

        shorterStorageMemory.save(shortDomainName2,longDomainName2,1);

        Thread.sleep(2000 );

        shorterStorageMemory.getLongDomain(shortDomainName2);
        shorterStorageMemory.getShortDomain(longDomainName2);

        shortDomainService.getShortDomainName("aa");

    }

    @Test
    void deleteLRUTest() throws InterruptedException {
        for(int i =0; i < 1000; i++){
            String longDomainName = horterString.generate(20);
            String shortDomainName = horterString.generate(8);
            shorterStorageMemory.save(shortDomainName,longDomainName,1);
            shorterStorageMemory.getShortDomain(longDomainName);
            shorterStorageMemory.getLongDomain(shortDomainName);
        }


    }


}
