package com.interview.assignment.service.impl;

import com.interview.assignment.domain.Application;
import com.interview.assignment.repository.ApplicationRepository;
import com.interview.assignment.service.ApplicationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  @Setter
  @Autowired
  private ApplicationRepository applicationRepository;

  @Override
  public Application findByAppId(String appId) {
    return applicationRepository.findByAppId(appId);
  }

}
