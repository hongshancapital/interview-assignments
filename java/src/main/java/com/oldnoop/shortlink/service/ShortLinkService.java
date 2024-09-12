package com.oldnoop.shortlink.service;

import com.google.common.base.Preconditions;
import com.oldnoop.shortlink.transfer.LinkTransfer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortLinkService {

	@Autowired
    private LinkTransfer transfer;
    
    private final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
    
    public List<String> create(List<String> longLinkList){
    	List<String> result = new ArrayList<>();
    	for(String longLink : longLinkList) {
            result.add(create(longLink));
    	}
    	return result;
    }
    
    public String create(String longLink){
    	Preconditions.checkState(StringUtils.isNotBlank(longLink), "链接为空");
        Preconditions.checkState(urlValidator.isValid(longLink), "链接不合法");
        return transfer.transfer(longLink);
    }

    public String search(String shortLink){
        return transfer.origin(shortLink);
    }

	public LinkTransfer getTransfer() {
		return transfer;
	}

	public void setTransfer(LinkTransfer transfer) {
		this.transfer = transfer;
	}

}
