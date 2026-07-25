package com.cyh.assignment.service;

public interface IDomainConvertorService {

    String getFullDomain(String shortDomain) throws Exception;

    String saveShortDomain(String longDomain) throws Exception;

}
