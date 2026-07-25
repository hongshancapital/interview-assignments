package com.example.demo.service;

import com.example.demo.dto.Response;

public interface IDomainNameTransferService {

    public Response<String> getShortDomainName(String longDomainName);
    
    public Response<String> getLongDomainName(String shortDomainName);
}
