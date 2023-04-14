package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.Response;
import com.example.demo.service.IDomainNameTransferService;

@SpringBootTest(classes = TransferApplication.class)
public class TransferApplicationTests {
	@Autowired
	private IDomainNameTransferService domainNameTransferService;
	
    // @Test
    public void getLongDomainNameTest() {
    	Response<String> response = domainNameTransferService.getLongDomainName("https://blog.csdn.net/weixin_39220472/article/details/87714756");
    	
    	if (response.getCode() == 0) {
    		System.out.println(response.getData());
    	} else {
    		System.out.println(response.getMessage());
    	}
    }

    @Test
    public void getShortDomainNameTest() {
    	Response<String> response = domainNameTransferService.getShortDomainName("https://blog.csdn.net/weixin_39220472/article/details/87714756_1111");
    	
    	if (response.getCode() == 0) {
    		System.out.println(response.getData());
    	} else {
    		System.out.println(response.getMessage());
    	}
    }
}
