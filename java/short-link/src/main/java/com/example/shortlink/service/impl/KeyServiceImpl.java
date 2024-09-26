package com.example.shortlink.service.impl;

import com.example.shortlink.config.AppConfig;
import com.example.shortlink.service.KeyService;
import com.example.shortlink.util.IdUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tianhao.lan
 * @date 2021-12-27.
 */
@Service
public class KeyServiceImpl implements KeyService, InitializingBean {

  @Autowired
  private AppConfig appConfig;

  private IdUtil idUtil;

  @Override
  public long getUniqueId() {
    return idUtil.nextId();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    idUtil = new IdUtil(appConfig.M_QPS_BIT);
  }
}
