package com.example.shortlink.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianhao.lan
 * @date 2021-12-29.
 */
@Configuration
@Slf4j
public class AppConfig {

  /**
   * guava缓存数量
   */
  @Value("${app.config.mQpsBit}")
  public Integer M_QPS_BIT= 7;

  /**
   * guava缓存数量
   */
  @Value("${app.config.guavaNum}")
  public Integer GUAVA_NUM;

  /**
   * 短链缓存时长
   */
  @Value("${app.config.expireSec}")
  public Long EXPIRE_SEC;

  /**
   * 机器码
   */
  @Value("${app.config.machineCode}")
  public String MACHINE_CODE;

}
