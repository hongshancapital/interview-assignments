package com.interview.assignment.service.impl;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.repository.ShortCodeClickRepository;
import com.interview.assignment.service.ShortCodeClickService;
import com.interview.assignment.util.DateUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShortCodeClickServiceImpl implements ShortCodeClickService {

  @Setter
  @Autowired
  private ShortCodeClickRepository shortCodeClickRepository;

  @Override
  public void record(ShortCode shortCode) {
    Date date = new Date();
    String day = DateUtil.format(date, "yyyy-MM-dd");
    String hour = DateUtil.format(date, "HH");
    shortCodeClickRepository.record(shortCode.getAppId(), shortCode.getCode(), day, hour);
  }

}
