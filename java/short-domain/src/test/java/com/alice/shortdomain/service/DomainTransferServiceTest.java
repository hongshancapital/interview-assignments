package com.alice.shortdomain.service;

import com.alice.shortdomain.ShortDomainApplication;
import com.alice.shortdomain.dto.RequestDTO;
import com.alice.shortdomain.dto.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author Alice [l1360737271@qq.com]
 * @date 2021/4/14 10:02
 */
@SpringBootTest(classes = ShortDomainApplication.class)
class DomainTransferServiceTest {

    private Logger log = LoggerFactory.getLogger(DomainTransferServiceTest.class);

    @Autowired
    private DomainTransferService domainTransferService;

    @Test
    void transferShort() throws IOException {
        RequestDTO request = new RequestDTO();
        request.setUrl("https://www.sequoiacap.com/china");
        ResponseDTO<String> response = domainTransferService.transferShort(request);
        log.info("{}", response);
    }

    @Test
    void search() {
        RequestDTO request = new RequestDTO();
        request.setUrl("abcd");
        ResponseDTO<String> response = domainTransferService.search(request);
        log.info("{}", response);
    }
}